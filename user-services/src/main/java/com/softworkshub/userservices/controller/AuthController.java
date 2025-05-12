package com.softworkshub.userservices.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.softworkshub.userservices.dto.LoginResponse;
import com.softworkshub.userservices.dto.LoginUser;
import com.softworkshub.userservices.dto.RegisterUser;
import com.softworkshub.userservices.model.User;
import com.softworkshub.userservices.services.impl.AuthServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {


    @Autowired
    private AuthServiceImpl authService;

    @PostMapping("/register")
    public ResponseEntity<User> crateUser(@RequestBody RegisterUser user) throws JsonProcessingException {
        return authService.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUser user) throws JsonProcessingException {
        return authService.loginUser(user);
    }

}
