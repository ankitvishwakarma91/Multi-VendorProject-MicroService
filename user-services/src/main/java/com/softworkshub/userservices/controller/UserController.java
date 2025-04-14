package com.softworkshub.userservices.controller;



import com.softworkshub.userservices.model.User;
import com.softworkshub.userservices.services.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String test(@RequestHeader("X-User-Role") String role) {
        log.info("Test endpoint hit by role: {}", role);
        return "Hello from User Service!";
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getUsers(@RequestHeader("X-User-Role") String role) {
        log.info("Roles of current user : {}",role);
        if (role.equals("ADMIN")) {
            return userService.getAll();
        }
        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
    }

}
