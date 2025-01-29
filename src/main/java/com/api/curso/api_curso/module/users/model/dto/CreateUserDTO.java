package com.api.curso.api_curso.module.users.model.dto;

import com.api.curso.api_curso.module.users.model.entity.UserEntity;


public record CreateUserDTO(String name, String email) {
    public UserEntity toEntity() {
        return new UserEntity(name, email);
    }
}
