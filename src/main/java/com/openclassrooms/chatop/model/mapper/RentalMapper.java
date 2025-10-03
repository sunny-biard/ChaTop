package com.openclassrooms.chatop.model.mapper;

import java.sql.Date;

import com.openclassrooms.chatop.model.dto.RentalCreateRequest;
import com.openclassrooms.chatop.model.entity.RentalModel;

public class RentalMapper {
    public static RentalModel toEntity(Integer id, RentalCreateRequest req, String picturePath) {
        RentalModel rental = new RentalModel();
        rental.setName(req.getName());
        rental.setSurface(req.getSurface());
        rental.setPrice(req.getPrice());
        rental.setPicture(picturePath);
        rental.setDescription(req.getDescription());
        rental.setOwner_id(id);
        rental.setCreated_at(new Date(System.currentTimeMillis()));
        rental.setUpdated_at(new Date(System.currentTimeMillis()));
        return rental;
    }
}
