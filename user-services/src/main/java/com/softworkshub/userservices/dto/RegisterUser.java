package com.softworkshub.userservices.dto;


import com.softworkshub.userservices.util.Role;
import lombok.Data;

@Data
public class RegisterUser {

    private String name;
    private String email;
    private String password;
    private String phone;
    private Role role;
}
