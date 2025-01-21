package com.api.curso.api_curso.modules.users.dto;

import java.util.UUID;

import com.api.curso.api_curso.modules.users.entity.Role;
import com.api.curso.api_curso.modules.users.entity.UserEntity;

public record UserDTO (UUID id, String name, String email, Role role) {
    public static UserDTO fromEntity(UserEntity userEntity) {
        return new UserDTO(
            userEntity.getId(),
            userEntity.getName(),
            userEntity.getEmail(),
            userEntity.getRole()
        );
    }
    

}
