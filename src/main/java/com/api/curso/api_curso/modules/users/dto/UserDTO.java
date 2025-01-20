package com.api.curso.api_curso.modules.users.dto;

import java.util.UUID;

import com.api.curso.api_curso.modules.users.entity.Role;

public class UserDTO {
    UUID id;
    String name;
    String email;
    Role role;

    public UserDTO(UUID id, String name, String email, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public UserDTO() {

    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setEmail(String email) {
        this.email = email;
    }

}
