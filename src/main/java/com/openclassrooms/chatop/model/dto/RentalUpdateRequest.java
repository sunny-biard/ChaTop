package com.openclassrooms.chatop.model.dto;

import lombok.Data;

@Data
public class RentalUpdateRequest {
    private String name;
    private Double surface;
    private Double price;
    private String description;
}
