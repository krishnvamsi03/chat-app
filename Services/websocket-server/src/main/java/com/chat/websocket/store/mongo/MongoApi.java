package com.chat.websocket.store.mongo;

import com.chat.websocket.converter.ConvertToJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.mongodb.client.model.Filters.eq;

@Service
public class MongoApi<V> {

    @Autowired
    private MongoClient mongoClient;

    @Autowired
    private MongoDatabase mongoDatabase;

    private static final ConvertToJson convertToJson = new ConvertToJson();

    public void createSession() {
        MongoCollection<Document> mongoCollection =
                mongoDatabase.getCollection("ActiveSessions");
    }

    public MongoCollection<Document> getMongoCollection(String collectionName) {
        return mongoDatabase.getCollection(collectionName);
    }

    public InsertOneResult createDocument(MongoCollection<Document> mongoCollection, String jsonDoc) {
        return mongoCollection.insertOne(Document.parse(jsonDoc));
    }

    public InsertOneResult createDocument(MongoCollection<Document> mongoCollection, V doc) throws JsonProcessingException {
        return mongoCollection.insertOne(Document.parse(convertToJson.getJsonString(doc)));
    }

    public DeleteResult deleteDocument(MongoCollection<Document> mongoCollection, String field, String value) {
        return mongoCollection.deleteOne(eq(field, value));
    }

    public Document getDocumentOnEq(String collection, String fieldName, V value) {
        return getMongoCollection(collection).find(eq(fieldName, value)).first();
    }
}
