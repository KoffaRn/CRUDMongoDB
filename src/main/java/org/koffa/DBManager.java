package org.koffa;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
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
            if (database != null) database.createCollection(collectionName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        collection = database.getCollection(collectionName);
        try {
            collection.createIndex(Indexes.compoundIndex(Indexes.text("name"),Indexes.text("adress")));
            collection.createIndex(Indexes.ascending("age","employee_number","customer_number"));
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Creates a new document in the collection
     * @param document The document to be created
     */
    public void create (Document document) {
        try {
            collection.insertOne(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a document from the collection
     * @param id The id of the document to be deleted
     */
    public void delete (String id) {
        collection.deleteOne(new Document("_id", new ObjectId(id)));
    }

    /**
     * Deletes all documents from the collection
     */
    public void dropCollection() {
        collection.drop();
    }

    /**
     * Returns all documents from the collection
     * @return All documents from the collection
     */
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

    /**
     * Returns all documents from the collection that match the field and term
     * @param field The field to be searched
     * @param term The term to be searched
     * @return All documents from the collection that match the field and term
     */
    public Document[] getByField (String field, String term) {
        Document query = new Document(field, term);
        return findDocuments(query);
    }

    /**
     * Returns a document from the collection that matches the id
     * @param id The id of the document to be returned
     * @return A document from the collection that matches the id
     */
    public Document read(String id) {
        return collection.find(new Document("_id", new ObjectId(id))).first();
    }

    /**
     * Returns all documents from the collection that match the field and term, can search for partial matches.
     * @param term The term to be searched
     * @return All documents from the collection that match the field and term
     */
    public Document[] search(String field, String term) {
        var filter = Filters.regex(field,term,"i");
        return findDocuments(filter);
    }
    public Document[] search(String field, int value) {
        var filter = Filters.eq(field,value);
        return findDocuments(filter);
    }

    /**
     * Returns all documents from the collection that match the filter
     * @param filter The filter to be used
     * @return  All documents from the collection that match the filter
     */
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

    /**
     * Updates a document in the collection
     * @param id The id of the document to be updated
     * @param document The document to be updated with
     */
    public void update (String id, Document document) {
        document.remove("_id");
        collection.replaceOne(new Document("_id", new ObjectId(id)), document);
    }
    /**
     * Creates a connection to the database
     * @param connectionUrl The connection url to the database
     * @return The database
     */
    private MongoDatabase getDatabase(String connectionUrl) {
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionUrl))
                .serverApi(serverApi)
                .build();
        try {
            MongoClient mongoClient = MongoClients.create(settings);
            return mongoClient.getDatabase(dbName);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}