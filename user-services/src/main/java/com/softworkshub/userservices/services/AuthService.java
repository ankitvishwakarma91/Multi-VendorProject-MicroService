package com.softworkshub.userservices.services;

import com.softworkshub.userservices.dto.LoginResponse;
import com.softworkshub.userservices.dto.LoginUser;
import com.softworkshub.userservices.dto.RegisterUser;
import com.softworkshub.userservices.model.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    public ResponseEntity<User> createUser(RegisterUser user);

    public ResponseEntity<LoginResponse> loginUser(LoginUser user);
}
