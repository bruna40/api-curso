package com.api.curso.api_curso.module.users.useCases;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.api.curso.api_curso.exceptions.EmailAlreadyExistsException;
import com.api.curso.api_curso.exceptions.UserIdNotFound;
import com.api.curso.api_curso.module.cursos.exceptions.UnauthorizedActionException;
import com.api.curso.api_curso.module.users.model.dto.UpdateUserDTO;
import com.api.curso.api_curso.module.users.model.entity.RoleEnum;
import com.api.curso.api_curso.module.users.model.entity.UserEntity;
import com.api.curso.api_curso.module.users.repository.UserRepository;

@Service
public class UserUseCase {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity getByEmail(String email){
        return userRepository.findByEmail(email)
        .orElseThrow(EmailAlreadyExistsException::new);
    }
    

    public UserEntity execute(UserEntity userEntity) {
        var password = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(password);

        return this.userRepository.save(userEntity);
    }
    

    public UserEntity getUserById(UUID id)   {
        return userRepository.findById(id).orElseThrow(UserIdNotFound::new);
    }

    private void applyUserUpdates(UserEntity userEntity, UpdateUserDTO updateUserDTO) {
        if (updateUserDTO.getName() != null && !userEntity.getName().equals(updateUserDTO.getName())) {
            userEntity.setName(updateUserDTO.getName());
        }

        if (updateUserDTO.getEmail() != null && !userEntity.getEmail().equals(updateUserDTO.getEmail())) {
            userEntity.setEmail(updateUserDTO.getEmail());
        }

        if (updateUserDTO.getPassword() != null) {
            var password = passwordEncoder.encode(updateUserDTO.getPassword());
            userEntity.setPassword(password);
        }
    }
    
    
    public UserEntity updateUser(UpdateUserDTO updateUserDTO, String email, RoleEnum role) {
        var user = getUserById(updateUserDTO.getId());

        if (role == RoleEnum.ADMIN) {
            applyUserUpdates(user, updateUserDTO);
            
        } else if(role == RoleEnum.PROFESSOR || role == RoleEnum.USER) {
            if (!user.getEmail().equals(email)) {
                throw new UnauthorizedActionException("Você só pode editar usuários que criou.");
            }
            applyUserUpdates(user, updateUserDTO);
        }  else {
            throw new UnauthorizedActionException("Você não tem permissão para editar este usuário.");
        }
        
        return userRepository.save(user);
    }

}
