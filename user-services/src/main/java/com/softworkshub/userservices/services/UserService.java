package com.softworkshub.userservices.services;


import com.softworkshub.userservices.dto.RegisterUser;
import com.softworkshub.userservices.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {



//    public ResponseEntity<User> updateUser(User user);

    public ResponseEntity<User> getUser(String id);

    public ResponseEntity<List<User>> getAll();

    public RegisterUser getUserByEmail(String email);
}
