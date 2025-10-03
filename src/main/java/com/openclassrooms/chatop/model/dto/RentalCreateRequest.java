package com.openclassrooms.chatop.model.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class RentalCreateRequest {
    private String name;
    private Double surface;
    private Double price;
    private MultipartFile picture;
    private String description;
}
