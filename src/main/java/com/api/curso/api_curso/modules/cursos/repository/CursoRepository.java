package com.api.curso.api_curso.modules.cursos.repository;

// import java.util.List;
// import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import com.api.curso.api_curso.modules.cursos.entity.CursoEntity;

public interface CursoRepository extends JpaRepository<CursoEntity, UUID> {

    Page<CursoEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<CursoEntity> findByCategoryContainingIgnoreCase(String category, Pageable pageable);

    Page<CursoEntity> findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(String name, String category,
            Pageable pageable);

    Page<CursoEntity> findByDeletedAtIsNull(Pageable pageable);
    // List<CursoEntity> findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(String name, String category);
    // List<CursoEntity> findByNameContainingIgnoreCase(String name);
    // List<CursoEntity> findByCategoryContainingIgnoreCase(String category);
    // Optional<CursoEntity> findById(UUID id);
    // List<CursoEntity> findByDeletedAtIsNull();
    // Page<CursoEntity> findByDeletedAtIsNull(Pageable pageable);
}
