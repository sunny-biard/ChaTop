package com.openclassrooms.chatop.controller;

import com.openclassrooms.chatop.service.JwtService;
import com.openclassrooms.chatop.service.UserService;
import com.openclassrooms.chatop.model.dto.LoginRequest;
import com.openclassrooms.chatop.model.dto.RegisterRequest;
import com.openclassrooms.chatop.model.dto.JwtResponse;
import com.openclassrooms.chatop.model.dto.UserDto;
import com.openclassrooms.chatop.model.entity.UserModel;
import com.openclassrooms.chatop.model.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private final AuthenticationManager authManager;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final UserService userService;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authManager, JwtService jwtService,
            UserService userService, PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest req) {
        authManager
                .authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

        UserDetails userDetails = userService.loadUserByUsername(req.getEmail());
        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest req) {
        if (userService.getUserByEmail(req.getEmail()) != null) {
            return ResponseEntity.status(400).body("Email already taken");
        }

        UserModel user = UserMapper.toEntity(req, passwordEncoder);

        userService.saveUser(user);

        UserDetails userDetails = userService.loadUserByUsername(req.getEmail());
        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/me")
    public ResponseEntity<Object> me(@AuthenticationPrincipal UserDetails userDetails) {
        UserModel user = userService.getUserByEmail(userDetails.getUsername());

        return ResponseEntity.ok(new UserDto(user.getName(), user.getEmail(), user.getCreated_at(), user.getUpdated_at()));
    }
}
