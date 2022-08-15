package com.chat.message.handler.store.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.mongodb.client.model.Filters.eq;

@Service
public class MongoApi<V> {

    @Autowired
    private MongoClient client;

    @Autowired
    private MongoDatabase defaultDatabase;

    public void createCollection(String collectionName) {
        defaultDatabase.createCollection(collectionName);
    }

    public MongoCollection<Document> getMongoCollection(String collectionName) {
        return defaultDatabase.getCollection(collectionName);
    }

    public void listAllCollection() {
        for (String name : defaultDatabase.listCollectionNames()) {
            System.out.println(name);
        }
    }

    public InsertOneResult createDocument(MongoCollection<Document> mongoCollection, String jsonDoc) {
        return mongoCollection.insertOne(Document.parse(jsonDoc));
    }

    public Document getDocumentOnEq(String collection, String fieldName, V value) {
        return getMongoCollection(collection).find(eq(fieldName, value)).first();
    }

}
