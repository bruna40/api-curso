package com.api.curso.api_curso.module.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.curso.api_curso.module.users.model.dto.LoginUserDTO;
import com.api.curso.api_curso.module.users.useCases.LoginUseCase;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginUseCase loginUseCase;


    @PostMapping
    @Tag(name = "Login", description = "Informacoes do login")
    public ResponseEntity<Object> login(@RequestBody LoginUserDTO loginUserDTO) {
        try {
            var user = loginUseCase.execute(loginUserDTO);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    
}
