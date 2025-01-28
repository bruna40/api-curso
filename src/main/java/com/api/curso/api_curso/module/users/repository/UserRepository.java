package com.api.curso.api_curso.module.users.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.curso.api_curso.module.users.model.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    List<UserEntity> findByDeletedAtIsNull();
    Optional<UserEntity> findById(UUID id);
}
