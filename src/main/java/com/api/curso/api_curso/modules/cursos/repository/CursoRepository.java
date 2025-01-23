package com.api.curso.api_curso.modules.cursos.repository;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.api.curso.api_curso.modules.cursos.entity.CursoEntity;

public interface CursoRepository extends JpaRepository<CursoEntity, UUID> {

    Page<CursoEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<CursoEntity> findByCategoryContainingIgnoreCase(String category, Pageable pageable);

    Page<CursoEntity> findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(String name, String category,
            Pageable pageable);

    Page<CursoEntity> findByDeletedAtIsNull(Pageable pageable);

    Page<CursoEntity> findAllByUserId(UUID userId, Pageable pageable);

}
