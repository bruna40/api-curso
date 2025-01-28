package com.api.curso.api_curso.module.users.model.dto;

import java.util.UUID;
import com.api.curso.api_curso.module.users.model.entity.RoleEnum;
import com.api.curso.api_curso.module.users.model.entity.UserEntity;


public record UserDTO (UUID id, String name, String email, RoleEnum role) {
    public static UserDTO fromEntity(UserEntity userEntity) {
        return new UserDTO(
            userEntity.getId(),
            userEntity.getName(),
            userEntity.getEmail(),
            userEntity.getRole()
        );
    }
    

}
