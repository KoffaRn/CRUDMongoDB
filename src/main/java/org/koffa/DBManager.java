package org.koffa;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.TextSearchOptions;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class DBManager {
    private final String dbName;
    MongoCollection<Document> collection;

    public DBManager (String dbName, String collectionName) {
        this.dbName = dbName;
        ConfigManager cm = new ConfigManager();
        String connectionUrl = cm.getConnectionUrl();
        MongoDatabase database = getDatabase(connectionUrl);
        try {
            database.createCollection(collectionName);
        } catch (Exception e) {

        }
        collection = database.getCollection(collectionName);
    }

    public void create (Document document) {
        try {
            collection.insertOne(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete (String id) {
        collection.deleteOne(new Document("_id", new ObjectId(id)));
    }

    public void dropCollection() {
        collection.drop();
    }

    public Document[] getAll() {
        FindIterable<Document> documents = collection.find();
        Document[] documentArray = new Document[(int) collection.countDocuments()];
        int i = 0;
        for (Document document : documents) {
            documentArray[i] = document;
            i++;
        }
        return documentArray;
    }

    public Document[] getByField (String field, String term) {
        Document query = new Document(field, term);
        return findDocuments(query);
    }

    public Document read(String id) {
        return collection.find(new Document("_id", new ObjectId(id))).first();
    }
    public Document[] search(String field, String term) {
        collection.createIndex(Indexes.text(field));
        TextSearchOptions options = new TextSearchOptions().caseSensitive(false);
        Bson filter = Filters.text(term, options);
        Document[] documentArray = findDocuments(filter);
        collection.dropIndexes();
        return documentArray;
    }

    private Document[] findDocuments(Bson filter) {
        FindIterable<Document> documents = collection.find(filter);
        Document[] documentArray = new Document[(int) collection.countDocuments(filter)];
        int i = 0;
        for(Document document : documents) {
            documentArray[i] = document;
            i++;
        }
        return documentArray;
    }

    public void update (String id, Document document) {
        document.remove("_id");
        collection.replaceOne(new Document("_id", new ObjectId(id)), document);
    }

    private MongoDatabase getDatabase(String connectionUrl) {
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionUrl))
                .serverApi(serverApi)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase(dbName);
        testConnection(database);
        return database;
    }

    private void testConnection(MongoDatabase database) {
        try {
            database.runCommand(new Document("ping", 1));
        }catch (MongoException e) {
            e.printStackTrace();
        }
    }
}
