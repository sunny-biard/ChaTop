package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.model.UserModel;
import com.openclassrooms.chatop.repository.UserRepository;
import com.openclassrooms.chatop.service.JwtService;

import lombok.Data;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private final AuthenticationManager authManager;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authManager, JwtService jwtService,
            UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        authManager
                .authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

        String token = jwtService.generateToken(req.getEmail());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already taken");
        }

        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already taken");
        }

        UserModel user = new UserModel();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setCreated_at(new Date(System.currentTimeMillis()));
        user.setUpdated_at(new Date(System.currentTimeMillis()));
        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        Optional<UserModel> userOpt = userRepository.findByUsername(userDetails.getUsername());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        UserModel user = userOpt.get();
        return ResponseEntity.ok(new UserDto(user.getId(), user.getUsername()));
    }

    @Data
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @Data
    public static class RegisterRequest {
        private String username;
        private String email;
        private String password;
    }

    @Data
    public static class JwtResponse {
        private String token;

        public JwtResponse(String token) {
            this.token = token;
        }
    }

    @Data
    public static class UserDto {
        private Integer id;
        private String username;

        public UserDto(Integer id, String username) {
            this.id = id;
            this.username = username;
        }
    }
}
