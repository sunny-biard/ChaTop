package com.openclassrooms.chatop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.chatop.model.RentalModel;

@Repository
public interface RentalRepository extends JpaRepository<RentalModel, Integer> {
    Optional<RentalModel> findById(Integer id);
}