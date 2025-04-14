package com.softworkshub.userservices.jwt;



import com.softworkshub.userservices.util.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private String username;
    private String email;
    private String password;
    private String name;
    private Role roles;
    private Collection<? extends GrantedAuthority> authorities;
}
