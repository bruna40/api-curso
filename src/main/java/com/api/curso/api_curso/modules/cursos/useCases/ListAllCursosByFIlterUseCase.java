package com.api.curso.api_curso.modules.cursos.useCases;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.curso.api_curso.modules.cursos.dto.CursoDTO;
import com.api.curso.api_curso.modules.cursos.dto.FiltroCursoDTO;
import com.api.curso.api_curso.modules.cursos.entity.CursoEntity;
import com.api.curso.api_curso.modules.cursos.repository.CursoRepository;


@Service
public class ListAllCursosByFIlterUseCase {  

    @Autowired
    private CursoRepository cursoRepository;


    public List<CursoDTO> execute(FiltroCursoDTO filtroCursoDTO) {
        List<CursoEntity> cursos;

        if (filtroCursoDTO.getName() != null && filtroCursoDTO.getCategory() != null) {
            cursos = cursoRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCase(filtroCursoDTO.getName(), filtroCursoDTO.getCategory());
        } else if (filtroCursoDTO.getName() != null) {
            cursos = cursoRepository.findByNameContainingIgnoreCase(filtroCursoDTO.getName());
        } else if (filtroCursoDTO.getCategory() != null) {
            cursos = cursoRepository.findByCategoryContainingIgnoreCase(filtroCursoDTO.getCategory());
        } else {
            cursos = cursoRepository.findByDeletedAtIsNull();
        }

        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso encontrado");
        }

        return cursos.stream()
                     .map(curso -> new CursoDTO(curso.getId(), curso.getName(), curso.getCategory()))
                     .collect(Collectors.toList());

    }
}
