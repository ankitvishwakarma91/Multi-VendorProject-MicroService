package com.softworkshub.userservices.services.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softworkshub.userservices.config.RabbitMQConfigU;
import com.softworkshub.userservices.dto.LoginResponse;
import com.softworkshub.userservices.dto.LoginUser;
import com.softworkshub.userservices.dto.RegisterUser;
import com.softworkshub.userservices.jwt.JwtUtil;
import com.softworkshub.userservices.model.User;
import com.softworkshub.userservices.repository.UserRepository;
import com.softworkshub.userservices.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitMQConfigU rabbitMQConfigU;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<User> createUser(RegisterUser user) throws JsonProcessingException {
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());

        if (byEmail.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        } else {
            User newUser = new User();
            newUser.setUserId(UUID.randomUUID().toString());
            newUser.setFullName(user.getName());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setRole(user.getRole());
            newUser.setPhone(user.getPhone());
            newUser = userRepository.save(newUser);
            userRepository.save(newUser);

            Map<String, String> payload = new HashMap<>();
            payload.put("to", newUser.getEmail());
            payload.put("subject", "Welcome to Softworkshub !");
            payload.put("message", "Dear " + newUser.getFullName() + "You have registered successfully!");

            rabbitTemplate.convertAndSend(
                    RabbitMQConfigU.EXCHANGE_NAME,
                    RabbitMQConfigU.ROUTING_KEY_USER_REGISTER,
                    new ObjectMapper().writeValueAsString(payload)
            );
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }
    }



    @Override
    public ResponseEntity<LoginResponse> loginUser(LoginUser user) throws JsonProcessingException {
//        User byEmail = userRepository.findByEmail(user.getEmail())
//                .orElseThrow(() ->new UsernameNotFoundException("User not found with username: " + user.getEmail()));
//
//        if (!passwordEncoder.matches(user.getPassword(), byEmail.getPassword())) {
//            throw new BadCredentialsException("Invalid password");
//        }
//
//        String token = jwtUtil.generateToken(byEmail);
//
//        LoginResponse authenticationSuccessful = new LoginResponse(token, "Authentication successful");
//        return new ResponseEntity<>(authenticationSuccessful, HttpStatus.OK);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
        }catch (AuthenticationException e) {
            e.printStackTrace();
           throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setMessage("Authentication Successful");

        Map<String, String> payload = new HashMap<>();
        payload.put("to", user.getEmail());
        payload.put("subject", "Login Alert - SoftWorks Hub");
        payload.put("message", "Hi " + user.getEmail() + ", you logged in successfully.");

        rabbitTemplate.convertAndSend(
                RabbitMQConfigU.EXCHANGE_NAME,
                RabbitMQConfigU.ROUTING_KEY_USER_LOGIN,
                new ObjectMapper().writeValueAsString(payload)
        );
        rabbitTemplate.convertAndSend(
                RabbitMQConfigU.EXCHANGE_NAME,
                RabbitMQConfigU.ROUTING_KEY_USER_LOGIN,
                new ObjectMapper().writeValueAsString(payload)
        );
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }


}
