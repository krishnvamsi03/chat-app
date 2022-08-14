package com.chat.websocket.store.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoClientInstance {

    @Value("${spring.data.mongodb.uri}")
    private String databaseUri;

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Bean
    public MongoClient getMongClient() {
        ConnectionString connectionString = new ConnectionString(databaseUri);
        MongoClientSettings mongoClientSettings =
                MongoClientSettings.builder().applyConnectionString(connectionString).build();
        return MongoClients.create(connectionString);
    }

    @Bean
    public MongoDatabase getDefaultMongoDb() {
        return getMongClient().getDatabase(getDatabaseName());
    }

    @Bean
    public String getDatabaseName() {
        return databaseName;
    }
}
