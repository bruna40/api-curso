package com.api.curso.api_curso.modules.cursos.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.curso.api_curso.modules.cursos.entity.CursoEntity;

public interface CursoRepository extends JpaRepository<CursoEntity, UUID> {

    List<CursoEntity> findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(String name, String category);
    List<CursoEntity> findByNameContainingIgnoreCase(String name);
    List<CursoEntity> findByCategoryContainingIgnoreCase(String category);
    Optional<CursoEntity> findById(UUID id);
    List<CursoEntity> findByDeletedAtIsNull();
}
