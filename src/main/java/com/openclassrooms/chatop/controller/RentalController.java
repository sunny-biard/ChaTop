package com.openclassrooms.chatop.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.openclassrooms.chatop.model.dto.RentalCreateRequest;
import com.openclassrooms.chatop.model.dto.RentalUpdateRequest;
import com.openclassrooms.chatop.service.RentalService;
import com.openclassrooms.chatop.service.UserService;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private UserService userService;

    public RentalController(RentalService rentalService, UserService userService) {
        this.rentalService = rentalService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Object> getRentals() {
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRentalById(@PathVariable Integer id) {
        return ResponseEntity.ok(rentalService.getRentalById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createRental(
            @AuthenticationPrincipal UserDetails userDetails,
            RentalCreateRequest req,
            @RequestPart(value = "picture", required = true) MultipartFile picture) throws IOException {

        req.setPicture(picture);

        return ResponseEntity.ok(rentalService.createRental(userService.getUserByEmail(userDetails.getUsername()).getId(), req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRental(@PathVariable Integer id, @AuthenticationPrincipal UserDetails userDetails, @RequestBody RentalUpdateRequest req) {
        return ResponseEntity.ok(rentalService.updateRental(id, userService.getUserByEmail(userDetails.getUsername()).getId(), req));
    }
}
