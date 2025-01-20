package com.api.curso.api_curso.modules.users.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.curso.api_curso.exceptions.UserNotFoundException;
import com.api.curso.api_curso.modules.users.dto.LoginUserDTO;
import com.api.curso.api_curso.modules.users.dto.LoginUserResponseDTO;
import com.api.curso.api_curso.modules.users.repository.UserRepository;
import com.api.curso.api_curso.providers.JWTUserProvider;


@Service
public class LoginUseCase {
   
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUserProvider jwtUserProvider;


    public LoginUserResponseDTO execute(LoginUserDTO loginUserDTO) {
        var user = this.userRepository.findByEmail(loginUserDTO.getEmail()).orElseThrow(UserNotFoundException::new);

        var passwordMatches = this.passwordEncoder.matches(loginUserDTO.getPassword(), user.getPassword());
        if(!passwordMatches) {
            throw new RuntimeException("Invalid password");
        }

        

        String token = jwtUserProvider.generateToken(user.getEmail(), user.getRole());
        
        var AuthUser = LoginUserResponseDTO.builder()
            .accessToken(token)
            .role(user.getRole())
            .build();

        return AuthUser;

    }
}
