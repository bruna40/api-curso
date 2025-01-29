package com.api.curso.api_curso.module.users.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.api.curso.api_curso.exceptions.EmailAlreadyExistsException;
import com.api.curso.api_curso.exceptions.UserIdNotFound;
import com.api.curso.api_curso.module.cursos.model.dto.CursoDTO;
import com.api.curso.api_curso.module.cursos.model.entity.CursoEntity;
import com.api.curso.api_curso.module.cursos.repository.CursoRepository;
import com.api.curso.api_curso.module.users.model.dto.UserDTO;
import com.api.curso.api_curso.module.users.model.entity.UserEntity;
import com.api.curso.api_curso.module.users.repository.UserRepository;

@Service
public class UserUseCase {

    private UserRepository userRepository;
    private CursoRepository cursoRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserUseCase(UserRepository userRepository, CursoRepository cursoRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.cursoRepository = cursoRepository;
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

    public CursoEntity createCurso(CursoEntity cursoEntity, UUID userId) throws UserIdNotFound{
        UserEntity user = getUserById(userId);
        cursoEntity.setUser(user);
        return cursoRepository.save(cursoEntity);
    }


    public Page<CursoDTO> listAllCursos(UUID userId, Pageable pageable)  throws UserIdNotFound {
        UserEntity user = getUserById(userId);
        UserDTO userDTO = UserDTO.fromEntity(user);

        return CursoDTO.fromEntityList(cursoRepository.findAllByUserId(userDTO.id(), pageable));
    }

    

}
