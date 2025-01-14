package com.api.curso.api_curso.modules.users.useCases;

import java.util.List;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.curso.api_curso.modules.users.dto.LoginUserDTO;
import com.api.curso.api_curso.modules.users.dto.LoginUserResponseDTO;
import com.api.curso.api_curso.modules.users.repository.UserRepository;
import com.api.curso.api_curso.providers.JWTUserProvider;

import ch.qos.logback.core.subst.Token;

@Service
public class LoginUseCase {
   
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUserProvider jwtUserProvider;


    public LoginUserResponseDTO execute(LoginUserDTO loginUserDTO) {
        var user = this.userRepository.findByEmail(loginUserDTO.getEmail()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        var passwordMatches = this.passwordEncoder.matches(loginUserDTO.getPassword(), user.getPassword());
        if(!passwordMatches) {
            throw new RuntimeException("Senha inválida");
        }

        

        String token = jwtUserProvider.generateToken(user.getEmail(), user.getRole());
        
        var AuthUser = LoginUserResponseDTO.builder()
            .accessToken(token)
            .role(user.getRole())
            .build();

        return AuthUser;

    }
}
