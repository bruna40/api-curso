package com.api.curso.api_curso.module.cursos.useCases;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.curso.api_curso.module.cursos.model.dto.CursoDTO;
import com.api.curso.api_curso.module.cursos.model.dto.FiltroCursoDTO;
import com.api.curso.api_curso.module.cursos.model.entity.CursoEntity;
import com.api.curso.api_curso.module.cursos.repository.CursoRepository;


@Service
public class ListAllCursosByFilterUseCase {  

    private static final Logger logger = LoggerFactory.getLogger(ListAllCursosByFilterUseCase.class);

    @Autowired
    private CursoRepository cursoRepository;

    public Page<CursoDTO> execute(FiltroCursoDTO filtroCursoDTO, Pageable pageable) {
        Page<CursoEntity> cursos = findCursoByFilter(filtroCursoDTO, pageable);

        if (cursos.isEmpty()) {
            logger.info("Nenhum curso encontrado para os filtros aplicados.");
        }        
       
        return CursoDTO.fromEntityList(cursos);
    }


    public Page<CursoEntity> findCursoByFilter(FiltroCursoDTO filtroCursoDTO, Pageable pageable) {
        if (filtroCursoDTO.getName() != null && filtroCursoDTO.getCategory() != null) {
            return cursoRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(filtroCursoDTO.getName(), filtroCursoDTO.getCategory(), pageable);
        } else if (filtroCursoDTO.getName() != null) {
            return cursoRepository.findByNameContainingIgnoreCase(filtroCursoDTO.getName(), pageable);
        } else if (filtroCursoDTO.getCategory() != null) {
            return cursoRepository.findByCategoryContainingIgnoreCase(filtroCursoDTO.getCategory(), pageable);
        } else {
            return cursoRepository.findByDeletedAtIsNull(pageable);
        }

    }
}
