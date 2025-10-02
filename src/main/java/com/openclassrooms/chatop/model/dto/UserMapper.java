package com.openclassrooms.chatop.model.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.openclassrooms.chatop.model.UserModel;

import java.sql.Date;

public class UserMapper {
    public static UserModel toEntity(RegisterRequest req, PasswordEncoder passwordEncoder) {
        UserModel user = new UserModel();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setCreated_at(new Date(System.currentTimeMillis()));
        user.setUpdated_at(new Date(System.currentTimeMillis()));
        return user;
    }
}
