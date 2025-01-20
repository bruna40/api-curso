package com.api.curso.api_curso.modules.users.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.curso.api_curso.exceptions.UserNotFoundException;
import com.api.curso.api_curso.modules.users.dto.UserDTO;
import com.api.curso.api_curso.modules.users.entity.UserEntity;
import com.api.curso.api_curso.modules.users.repository.UserRepository;

@Service
public class UserUseCase {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    

    public UserEntity execute(UserEntity userEntity) {
        this.userRepository.findByEmail(userEntity.getEmail()).ifPresent(user -> {
            throw new RuntimeException("Email já cadastrado");
        });

        var password = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(password);

        return this.userRepository.save(userEntity);
    }

    public UserDTO getUserById(UUID id) {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
