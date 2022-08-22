package com.chat.auth.services;

import com.chat.auth.store.mongo.MongoApi;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServices implements UserDetailsService {

    @Autowired
    private MongoApi mongoApi;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Document doc = mongoApi.getDocumentOnEq("user", "username", username);
        return new User(doc.getString("username"), doc.getString("password"),
                new ArrayList<>());
    }
}
