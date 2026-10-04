package com.example.demo.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.DAO.UserRepository;
import com.example.demo.entity.User;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {     //Spring Security understands this interface called UserDetailsService

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository                 //fetch user from my database
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(     //add details to spring security user
                user.getUsername(),
                user.getPassword(),
                Collections.emptyList()
        );
    }
}