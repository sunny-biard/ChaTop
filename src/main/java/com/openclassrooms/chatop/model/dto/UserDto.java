package com.openclassrooms.chatop.model.dto;

import lombok.Data;

import java.sql.Date;

@Data
public  class UserDto {
    private String name;
    private String email;
    private Date created_at;
    private Date updated_at;

    public UserDto(String name, String email, Date created_at, Date updated_at) {
        this.name = name;
        this.email = email;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
