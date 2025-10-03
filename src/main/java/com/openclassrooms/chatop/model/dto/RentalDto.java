package com.openclassrooms.chatop.model.dto;

import lombok.Data;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

@Data
public  class RentalDto {
    private String name;
    private Double surface;
    private Double price;
    private MultipartFile picture;
    private String description;
    private Date created_at;
    private Date updated_at;

    public RentalDto(String name, Double surface, Double price, MultipartFile picture, String description, Date created_at, Date updated_at) {
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.picture = picture;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
