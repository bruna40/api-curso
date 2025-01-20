package com.api.curso.api_curso.modules.users.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.curso.api_curso.modules.users.dto.UserDTO;
import com.api.curso.api_curso.modules.users.entity.UserEntity;
import com.api.curso.api_curso.modules.users.useCases.UserUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "Informacoes dos usuarios")
public class UserController {

    @Autowired
    private UserUseCase userUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody UserEntity userEntity) {
        try {
            var user = userUseCase.execute(userEntity);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        try {
            var user = userUseCase.getUserById(id);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    
}
