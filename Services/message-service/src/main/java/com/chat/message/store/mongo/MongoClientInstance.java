package com.chat.message.store.mongo;

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
    private String connectionUri;

    @Value("${spring.data.mongodb.database}")
    private String defaultDBName;

    @Bean
    public MongoClient getMongoClient() {
        ConnectionString connectionString = new ConnectionString(connectionUri);
        MongoClientSettings mongoSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString).build();
        return MongoClients.create(mongoSettings);
    }

    @Bean
    public MongoDatabase getDefaultMongoDb() {
        return getMongoClient().getDatabase(defaultDBName);
    }

    @Bean
    public String getDefaultDBName() {
        return defaultDBName;
    }
}
