package com.softworkshub.userservices.jwt;



import com.softworkshub.userservices.dto.RegisterUser;
import com.softworkshub.userservices.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        try {

            RegisterUser user = userService.getUserByEmail(email);

            return new CustomUserDetails(
                    user.getEmail(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getName(),
                    user.getRole(),
                    null
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
