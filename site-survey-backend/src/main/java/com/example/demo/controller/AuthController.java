package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* =========================
       REGISTER
    ========================= */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Email already registered");
        }

        userRepository.save(user);
        return ResponseEntity.ok("Registration successful");
    }

    /* =========================
       LOGIN
    ========================= */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        boolean exists = userRepository
                .findByEmailAndPassword(
                        user.getEmail().trim(),
                        user.getPassword().trim()
                )
                .isPresent();

        if (!exists) {
            return ResponseEntity
                    .status(401)
                    .body(Map.of("message", "INVALID_CREDENTIALS"));
        }

        return ResponseEntity.ok(
                Map.of(
                        "message", "SUCCESS",
                        "email", user.getEmail(),
                        "role", "ENGINEER"   // change to ADMIN if needed
                )
        );
    }
}
