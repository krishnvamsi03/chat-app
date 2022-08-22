package com.chat.auth.services;

import com.chat.auth.converter.ConvertToJson;
import com.chat.auth.model.UserRegisterRequest;
import com.chat.auth.store.mongo.MongoApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.result.InsertOneResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    @Autowired
    private MongoApi mongoApi;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ConvertToJson convertToJson;

    public InsertOneResult register(UserRegisterRequest registerRequest) throws JsonProcessingException {
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        return mongoApi.createDocument(mongoApi.getMongoCollection("user"),
                convertToJson.getJsonString(registerRequest));
    }
}
