package com.api.curso.api_curso.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private static final String[] PERMIT_ALL_LIST = {
      "/swagger-ui/**",
      "/v3/api-docs/**",
      "/swagger-resource/**",
      "/actuator/**"
    };

    @Autowired
    private SecurityUserFilter securityUserFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> {
                auth
                    .requestMatchers(HttpMethod.POST, "/users").permitAll()
                    .requestMatchers(HttpMethod.POST, "/login").permitAll()          
                    .requestMatchers(HttpMethod.GET, "/cursos").hasAnyRole("USER", "ADMIN", "PROFESSOR")
                    .requestMatchers(HttpMethod.POST, "/cursos").hasAnyRole("ADMIN", "PROFESSOR")
                    .requestMatchers(HttpMethod.GET, "/cursos/**").hasAnyRole("USER", "ADMIN", "PROFESSOR")
                    .requestMatchers(HttpMethod.PUT, "/cursos/**").hasAnyRole("ADMIN", "PROFESSOR")
                    .requestMatchers(HttpMethod.DELETE, "/cursos/**").hasAnyRole("ADMIN", "PROFESSOR")
                    .requestMatchers(HttpMethod.GET, "/purchase").hasAnyRole("USER", "ADMIN", "PROFESSOR")
                    .requestMatchers(HttpMethod.POST, "/purchase").hasAnyRole("ADMIN", "USER", "PROFESSOR")
                    .requestMatchers(HttpMethod.GET, "/purchase/**").hasAnyRole("USER", "ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/purchase/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/purchase/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/users/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/users/**").hasAnyRole("ADMIN", "PROFESSOR")
                    .requestMatchers(HttpMethod.PUT, "/users/**").hasAnyRole("USER", "ADMIN", "PROFESSOR")
                    .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                    .requestMatchers(PERMIT_ALL_LIST).permitAll()
                    .anyRequest().authenticated();
            })
            .addFilterBefore(securityUserFilter, BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
