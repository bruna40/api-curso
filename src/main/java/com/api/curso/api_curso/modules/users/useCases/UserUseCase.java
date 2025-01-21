package com.api.curso.api_curso.modules.users.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.curso.api_curso.exceptions.UserNotFoundException;
import com.api.curso.api_curso.modules.cursos.entity.CursoEntity;
import com.api.curso.api_curso.modules.cursos.repository.CursoRepository;
import com.api.curso.api_curso.modules.users.entity.UserEntity;
import com.api.curso.api_curso.modules.users.repository.UserRepository;

@Service
public class UserUseCase {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CursoRepository cursoRepository;

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

    public UserEntity getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        
    }

    public CursoEntity createCurso(CursoEntity cursoEntity, UUID userId) {
        UserEntity user = getUserById(userId);
        System.out.println(user);
        cursoEntity.setUser(user);
        return cursoRepository.save(cursoEntity);
    }


}
