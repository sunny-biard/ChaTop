package com.openclassrooms.chatop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.chatop.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}
