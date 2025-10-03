package com.openclassrooms.chatop.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.chatop.model.dto.RentalCreateRequest;
import com.openclassrooms.chatop.model.dto.RentalUpdateRequest;
import com.openclassrooms.chatop.model.entity.RentalModel;
import com.openclassrooms.chatop.model.mapper.RentalMapper;
import com.openclassrooms.chatop.repository.RentalRepository;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    public List<RentalModel> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Optional<RentalModel> getRentalById(Integer id) {
        return rentalRepository.findById(id);
    }

    public RentalModel createRental(Integer userId, RentalCreateRequest req) {
        String picturePath = null;
        String uploadDir = "pictures/";
        String fileName = UUID.randomUUID() + "_" + req.getPicture().getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        try {
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, req.getPicture().getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }

        picturePath = "/pictures/" + fileName;

        RentalModel rental = RentalMapper.toEntity(userId, req, picturePath);
        
        return rentalRepository.save(rental);
    }

    public RentalModel updateRental(Integer rentalId, Integer userId, RentalUpdateRequest updatedRental) {
        RentalModel existingRental = rentalRepository.findById(rentalId).orElseThrow();

        if (existingRental.getOwner_id() != (userId)) {
            throw new RuntimeException("Unauthorized : You are not the owner of this rental.");
        } else {
            existingRental.setName(updatedRental.getName());
            existingRental.setSurface(updatedRental.getSurface());
            existingRental.setPrice(updatedRental.getPrice());
            existingRental.setDescription(updatedRental.getDescription());

            return rentalRepository.save(existingRental);
        }
    }
}
