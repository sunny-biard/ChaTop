package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.model.UserModel;
import com.openclassrooms.chatop.repository.UserRepository;
import com.openclassrooms.chatop.service.JwtService;
import com.openclassrooms.chatop.model.dto.LoginRequest;
import com.openclassrooms.chatop.model.dto.RegisterRequest;
import com.openclassrooms.chatop.model.dto.JwtResponse;
import com.openclassrooms.chatop.model.dto.UserDto;
import com.openclassrooms.chatop.model.dto.UserMapper;

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
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.status(400).body("Email already taken");
        }

        UserModel user = UserMapper.toEntity(req, passwordEncoder);
        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<UserModel> userOpt = userRepository.findByEmail(userDetails.getUsername());

        UserModel user = userOpt.get();
        return ResponseEntity.ok(new UserDto(user.getName(), user.getEmail(), user.getCreated_at(), user.getUpdated_at()));
    }
}
