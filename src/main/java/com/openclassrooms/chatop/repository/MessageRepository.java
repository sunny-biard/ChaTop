package com.openclassrooms.chatop.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.chatop.model.entity.MessageModel;

@Repository
public interface MessageRepository extends CrudRepository<MessageModel, Integer> {
    Optional<MessageModel> findById(Integer id);
}