package com.api.curso.api_curso.modules.users.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.curso.api_curso.modules.users.entity.UserEntity;
import com.api.curso.api_curso.modules.users.repository.UserRepository;

@Service
public class UserUseCase {
    
    @Autowired
    private UserRepository userRepository;
    

    public UserEntity execute(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
}
