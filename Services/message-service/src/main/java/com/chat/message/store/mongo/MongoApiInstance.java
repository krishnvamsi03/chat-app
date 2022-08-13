package com.chat.message.store.mongo;

import com.chat.message.store.mongo.MongoApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoApiInstance {

    @Bean
    public MongoApi getMongoApi() {
        return new MongoApi();
    }
}
