package com.openclassrooms.chatop.model.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "rentals")
public class RentalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private Double surface;

    @Column
    private Double price;

    @Column
    private String picture;

    @Column
    private String description;

    @Column
    private int owner_id;

    @Column
    private Date created_at;

    @Column
    private Date updated_at;
}
