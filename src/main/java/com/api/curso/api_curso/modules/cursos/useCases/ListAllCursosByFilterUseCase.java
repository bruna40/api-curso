package com.api.curso.api_curso.modules.cursos.useCases;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.curso.api_curso.modules.cursos.dto.CursoDTO;
import com.api.curso.api_curso.modules.cursos.dto.FiltroCursoDTO;
import com.api.curso.api_curso.modules.cursos.entity.CursoEntity;
import com.api.curso.api_curso.modules.cursos.repository.CursoRepository;


@Service
public class ListAllCursosByFilterUseCase {  

    private static final Logger logger = LoggerFactory.getLogger(ListAllCursosByFilterUseCase.class);

    @Autowired
    private CursoRepository cursoRepository;

    public List<CursoDTO> execute(FiltroCursoDTO filtroCursoDTO) {
        List<CursoEntity> cursos = findCursoByFilter(filtroCursoDTO);

        if (cursos.isEmpty()) {
            logger.info("Nenhum curso encontrado para os filtros aplicados.");
        }        
        return cursos.stream()
                     .map(curso -> new CursoDTO(curso.getId(), curso.getName(), curso.getCategory(), curso.isActive(), curso.getCreatedAt(), curso.getUpdatedAt()))
                     .collect(Collectors.toList());
    }


    public List<CursoEntity> findCursoByFilter(FiltroCursoDTO filtroCursoDTO) {
        if (filtroCursoDTO.getName() != null && filtroCursoDTO.getCategory() != null) {
            return cursoRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(filtroCursoDTO.getName(), filtroCursoDTO.getCategory());
        } else if (filtroCursoDTO.getName() != null) {
            return cursoRepository.findByNameContainingIgnoreCase(filtroCursoDTO.getName());
        } else if (filtroCursoDTO.getCategory() != null) {
            return cursoRepository.findByCategoryContainingIgnoreCase(filtroCursoDTO.getCategory());
        } else {
            return cursoRepository.findByDeletedAtIsNull();
        }

    }
}
