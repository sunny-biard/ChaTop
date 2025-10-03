package com.openclassrooms.chatop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.chatop.model.entity.RentalModel;

@Repository
public interface RentalRepository extends CrudRepository<RentalModel, Integer> {
    Optional<RentalModel> findById(Integer id);
    List<RentalModel> findAll();
}