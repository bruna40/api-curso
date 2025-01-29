package com.api.curso.api_curso.module.users.model.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "users")
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "O campo [name] é obrigatório")
    @Schema(example = "Ada Lovelace", requiredMode = RequiredMode.REQUIRED, description = "Nome do candidato")
    private String name;

    @NotNull(message = "O campo [email] é obrigatório")
    @Email(message = "O campo [email] deve conter um e-mail válido")
    @Schema(example = "ada@email.com", requiredMode = RequiredMode.REQUIRED, description = "E-mail do candidato")
    private String email;

    @NotNull(message = "O campo [password] é obrigatório")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", 
        message = "A senha deve ter no mínimo 8 caracteres, incluindo uma letra maiúscula, uma minúscula, um número e um caractere especial (@$!%*?&)")
    @Schema(example = "0123456789", requiredMode = RequiredMode.REQUIRED, description = "Senha do candidato")
    private String password;


    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    @NotNull(message = "O campo [role] é obrigatório")
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    public UserEntity(String name, String email) {
        this.name = name;
        this.email = email;

    }

    public UserEntity() {
    }

}
