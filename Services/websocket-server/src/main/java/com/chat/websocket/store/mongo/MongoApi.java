package com.chat.websocket.store.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;

@Service
public class MongoApi {

    @Autowired
    private MongoClient mongoClient;

    @Autowired
    private MongoDatabase mongoDatabase;

    public void createSession() {
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("Session");
    }
}
