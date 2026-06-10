package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DAO.UserRepository;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.entity.User;
import com.example.demo.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody User user) {

        // check if username already exists
        if (userRepository
                .findByUsername(user.getUsername())
                .isPresent()) {

            return ResponseEntity.badRequest()
                    .body("Username already exists");
        }

        // encrypt password
        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );

        userRepository.save(user);

        return ResponseEntity.ok(
                "User Registered Successfully"
        );
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) {

        User user = userRepository
                .findByUsername(
                        request.getUsername()
                )
                .orElse(null);

        if (user == null) {

            return ResponseEntity.badRequest()
                    .body("User not found");
        }

        boolean matches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!matches) {

            return ResponseEntity.badRequest()
                    .body("Invalid Password");
        }

        String token =
                JwtUtil.generateToken(
                        user.getUsername()
                );

        return ResponseEntity.ok(
                new LoginResponse(token)
        );
    }
}