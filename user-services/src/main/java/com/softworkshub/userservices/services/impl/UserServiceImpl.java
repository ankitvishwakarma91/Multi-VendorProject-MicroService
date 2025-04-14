package com.softworkshub.userservices.services.impl;


import com.softworkshub.userservices.dto.RegisterUser;
import com.softworkshub.userservices.model.User;
import com.softworkshub.userservices.repository.UserRepository;
import com.softworkshub.userservices.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;


    @Override
    public ResponseEntity<User> getUser(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<User>> getAll() {
        List<User> all = userRepository.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Override
    public RegisterUser getUserByEmail(String email) {
        User byEmail = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        RegisterUser user = new RegisterUser();
        user.setEmail(byEmail.getEmail());
        user.setPhone(byEmail.getPhone());
        user.setPassword(byEmail.getPassword());
        user.setName(byEmail.getFullName());
        user.setRole(byEmail.getRole());
        return user;
    }


}
