package com.chat.auth.controller;

import com.chat.auth.model.JwtRequest;
import com.chat.auth.model.JwtResponse;
import com.chat.auth.model.Response;
import com.chat.auth.model.UserRegisterRequest;
import com.chat.auth.services.UserRegistrationService;
import com.chat.auth.utility.JwtUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.result.InsertOneResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtility jwtUtility;
    @Autowired
    private UserRegistrationService userRegistrationService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String getHome() {
        return "Hello world";
    }

    @PostMapping("/authenticate")
    public JwtResponse getToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws Exception {
        String header = authorization.split(" ")[1];
        String base64 = new String(Base64.getDecoder().decode(header));
        String userName = base64.split(":")[0];
        String password = base64.split(":")[1];
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        } catch (Exception e) {
            throw new Exception("BAD_CREDENTIALS");
        }

        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(userName);
        final String token = jwtUtility.generateToken(userDetails);
        return new JwtResponse(token);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequest userRegisterRequest) throws JsonProcessingException {

        InsertOneResult res =
                userRegistrationService.register(userRegisterRequest);
        return new ResponseEntity<>(new Response(res.getInsertedId().asObjectId().getValue().toString()),
                HttpStatus.CREATED);
    }

}
