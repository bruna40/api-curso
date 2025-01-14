package com.api.curso.api_curso.modules.users.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.curso.api_curso.modules.users.entity.UserEntity;
import com.api.curso.api_curso.modules.users.useCases.UserUseCase;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
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
    
}
