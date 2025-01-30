package com.api.curso.api_curso.module.cursos.useCases;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.api.curso.api_curso.module.cursos.exceptions.CursoNotFoundException;
import com.api.curso.api_curso.module.cursos.exceptions.UnauthorizedActionException;
import com.api.curso.api_curso.module.cursos.model.dto.CursoDTO;
import com.api.curso.api_curso.module.cursos.model.dto.UpdateCursoDTO;
import com.api.curso.api_curso.module.cursos.model.entity.CursoEntity;
import com.api.curso.api_curso.module.cursos.model.enums.CategoryEnum;
import com.api.curso.api_curso.module.cursos.repository.CursoRepository;
import com.api.curso.api_curso.module.users.model.dto.UserDTO;
import com.api.curso.api_curso.module.users.model.entity.RoleEnum;
import com.api.curso.api_curso.module.users.model.entity.UserEntity;
import com.api.curso.api_curso.module.users.useCases.UserUseCase;


@Service
public class CursoUseCase {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UserUseCase userUseCase;

    public CursoEntity execute(CursoEntity cursoEntity) {
        return cursoRepository.save(cursoEntity);
    }

    public CursoEntity getById(UUID id) {
       return cursoRepository.findById(id).orElseThrow(() -> new CursoNotFoundException("Curso não encontrado"));
    }

    private void applyUpdates(CursoEntity cursoEntity, UpdateCursoDTO updateCursoDTO) {
    if (updateCursoDTO.getName() != null && !Objects.equals(cursoEntity.getName(), updateCursoDTO.getName())) {
        cursoEntity.setName(updateCursoDTO.getName());
    }

    if (updateCursoDTO.getCategory() != null) {
        try {
            CategoryEnum categoryEnum = updateCursoDTO.getCategory();
            if (!Objects.equals(cursoEntity.getCategory(), categoryEnum)) {
                cursoEntity.setCategory(categoryEnum);
            }
        } catch (Exception e) {
            throw e;
        }
    }
    if (updateCursoDTO.getPrice() != null && !Objects.equals(cursoEntity.getPrice(), updateCursoDTO.getPrice())) {
        cursoEntity.setPrice(updateCursoDTO.getPrice());
    }

    cursoEntity.setUpdatedAt(LocalDateTime.now());
}

    public CursoEntity updatedCurso(UpdateCursoDTO updateCursoDTO, String email, RoleEnum role) {
        var cursoEntity = getById(updateCursoDTO.getId());
        if (role == RoleEnum.ADMIN) {
            applyUpdates(cursoEntity, updateCursoDTO);
        } else if (role == RoleEnum.PROFESSOR) {
            if (!cursoEntity.getUser().getEmail().equals(email)) {
                throw new UnauthorizedActionException("Você só pode editar cursos que criou.");
            }
            applyUpdates(cursoEntity, updateCursoDTO);
        } else {
            throw new UnauthorizedActionException("Você não tem permissão para editar este curso.");
        }

        

        return cursoRepository.save(cursoEntity);
    }

    public void toggleActive(UUID id) {
        var cursoEntity = getById(id);
        cursoEntity.setActive(!cursoEntity.isActive());
        cursoRepository.save(cursoEntity);
    }

    public void delete(UUID id, String email, RoleEnum role) {
        var cursoEntity = getById(id);
        if (cursoEntity.getDeletedAt() != null) {
            throw new CursoNotFoundException("Curso já foi excluído.");
        }
        if (role == RoleEnum.ADMIN) {
            cursoEntity.setDeletedAt(LocalDateTime.now());
            cursoRepository.save(cursoEntity);
        }  else if (role == RoleEnum.PROFESSOR) {
            if (!cursoEntity.getUser().getEmail().equals(email)) { 
                throw new UnauthorizedActionException("Você não tem permissão para deletar este curso.");
            }
            cursoEntity.setDeletedAt(LocalDateTime.now());
            cursoRepository.save(cursoEntity);
        } 
        
        else {
            throw new UnauthorizedActionException("Você não tem permissão para deletar cursos.");
        }
    }
    

    public Page<CursoEntity> listAllPageable(int page, int size) {
       Pageable pageable = PageRequest.of(page, size);
       return cursoRepository.findByDeletedAtIsNull(pageable);
    }

    public CursoEntity createCurso(CursoEntity cursoEntity, UUID userId) {
        UserEntity user = userUseCase.getUserById(userId);
        cursoEntity.setUser(user);
        return cursoRepository.save(cursoEntity);
    }


    public Page<CursoDTO> listAllCursos(UUID userId, Pageable pageable) {
        UserEntity user = userUseCase.getUserById(userId);
        UserDTO userDTO = UserDTO.fromEntity(user);

        return CursoDTO.fromEntityList(cursoRepository.findAllByUserId(userDTO.id(), pageable));
    }

}
