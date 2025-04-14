package com.softworkshub.userservices.services.impl;


import com.softworkshub.userservices.dto.LoginResponse;
import com.softworkshub.userservices.dto.LoginUser;
import com.softworkshub.userservices.dto.RegisterUser;
import com.softworkshub.userservices.jwt.JwtUtil;
import com.softworkshub.userservices.model.User;
import com.softworkshub.userservices.repository.UserRepository;
import com.softworkshub.userservices.services.AuthService;
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

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

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
    public ResponseEntity<User> createUser(RegisterUser user) {
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
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }
    }



    @Override
    public ResponseEntity<LoginResponse> loginUser(LoginUser user) {
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
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }


}
