package com.security.xo.services;

import com.security.xo.type.PostUserDetail;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final JdbcUserDetailsManager jdbcUserDetailsManager;
  private final PasswordEncoder passwordEncoder;

    public UserService(UserDetailsService userDetailsManager, PasswordEncoder passwordEncoder) {
        this.jdbcUserDetailsManager = (JdbcUserDetailsManager)userDetailsManager;
        this.passwordEncoder = passwordEncoder;

    }
    public boolean userExists(String username){
        return jdbcUserDetailsManager.userExists(username);
    }
    public void register(String username,String password){
        UserDetails userDetails= User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles("USER")
                .build();
        jdbcUserDetailsManager.createUser(userDetails);
    }
    public void register(PostUserDetail p){
        register(p.username(), p.password());
    }

}
