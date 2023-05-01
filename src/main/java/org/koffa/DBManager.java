package org.koffa;

import com.mongodb.*;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

public class DBManager {
    MongoCollection collection;
    private final String dbName;

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
    private boolean testConnection(MongoDatabase database) {
        try {
            database.runCommand(new Document("ping", 1));
            return true;
        }catch (MongoException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void create (Document document) {
        try {
            collection.insertOne(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Document read(String id) {
        return (Document) collection.find(new Document("_id", new ObjectId(id))).first();
    }
    public void update (String id, Document document) {
        document.remove("_id");
        collection.replaceOne(new Document("_id", new ObjectId(id)), document);
    }
    public void delete (String id) {
        collection.deleteOne(new Document("_id", new ObjectId(id)));
    }
    public Document[] getByField (String field, String term) {
        Document query = new Document(field, term);
        FindIterable<Document> documents = collection.find(query);
        Document[] documentArray = new Document[(int) collection.countDocuments(query)];
        int i = 0;
        for (Document document : documents) {
            documentArray[i] = document;
            i++;
        }
        return documentArray;
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
    public void dropCluster() {
        collection.drop();
    }
}
