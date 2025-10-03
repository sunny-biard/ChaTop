package com.openclassrooms.chatop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.chatop.model.entity.UserModel;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Integer> {
    UserModel findByEmail(String email);
}
