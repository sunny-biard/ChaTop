package com.openclassrooms.chatop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.chatop.model.MessageModel;

@Repository
public interface MessageRepository extends JpaRepository<MessageModel, Integer> {
    Optional<MessageModel> findById(Integer id);
}