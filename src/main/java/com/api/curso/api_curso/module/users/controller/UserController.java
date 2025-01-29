package com.api.curso.api_curso.module.users.controller;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.api.curso.api_curso.exceptions.EmailAlreadyExistsException;
import com.api.curso.api_curso.exceptions.UserIdNotFound;
import com.api.curso.api_curso.module.cursos.exceptions.CursoNotFoundException;
import com.api.curso.api_curso.module.cursos.exceptions.UnauthorizedActionException;
import com.api.curso.api_curso.module.cursos.model.dto.CreateCursoDTO;
import com.api.curso.api_curso.module.cursos.model.dto.CursoDTO;
import com.api.curso.api_curso.module.cursos.model.entity.CursoEntity;
import com.api.curso.api_curso.module.cursos.useCases.CursoUseCase;
import com.api.curso.api_curso.module.purchase.model.dto.CreatePurchaseDTO;
import com.api.curso.api_curso.module.purchase.model.dto.PurchaseDTO;
import com.api.curso.api_curso.module.purchase.useCase.PurchaseUseCase;
import com.api.curso.api_curso.module.users.model.dto.UserDTO;
import com.api.curso.api_curso.module.users.model.entity.UserEntity;
import com.api.curso.api_curso.module.users.useCases.UserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;





@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "Informações dos usuarios")
public class UserController {

    @Autowired
    private UserUseCase userUseCase;

    @Autowired
    private PurchaseUseCase purchaseUseCase;

    @Autowired
    private CursoUseCase cursoUseCase;

    @PostMapping
    public ResponseEntity<UserEntity> create(@Valid @RequestBody UserEntity userEntity) {
        try {
            UserEntity user = userUseCase.execute(userEntity);
            return ResponseEntity.ok().body(user);
        } catch (EmailAlreadyExistsException e) {
           throw e;
        }
    }



    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        try {
            var user = userUseCase.getUserById(id);
        
            UserDTO userDTO = UserDTO.fromEntity(user);
            return ResponseEntity.ok().body(userDTO);
        } catch (UserIdNotFound e) {
            throw e;
        }
    }

    @PostMapping("/{id}/cursos")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSOR')")
    @Operation(summary = "Criar curso", description = "Apenas pessoas com o perfil de administrador podem criar cursos")
    @ApiResponses({
    @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = CreateCursoDTO.class))
        }),
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<CursoDTO> createCurso(@PathVariable UUID id, @Valid @RequestBody CreateCursoDTO createCursoDTO) {
        CursoDTO curso  =  CursoDTO.fromEntity(userUseCase.createCurso(createCursoDTO.toEntity(), id));
        return  ResponseEntity.status(HttpStatus.CREATED).body(curso);
    } 

    @GetMapping("/{id}/cursos")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Listar cursos", description = "Pessoas com o perfil de administrador ou usuario podem listar cursos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
                @Content(array = @ArraySchema(schema = @Schema(implementation = CursoDTO.class)))
        }),
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> listCursos(
        @PathVariable UUID id,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        try {
            PageRequest pageable = PageRequest.of(page, size);
            var cursos = userUseCase.listAllCursos(id, pageable);
            return ResponseEntity.ok().body(cursos);
        } catch (UserIdNotFound e) {
            throw e;
        }
    }

    @PostMapping("/{id}/purchase")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<PurchaseDTO> execute(@PathVariable UUID id, @RequestBody CreatePurchaseDTO purchaseEntity) {
        
        PurchaseDTO createPurchase = PurchaseDTO.fromEntity(purchaseUseCase.createPurchase(purchaseEntity.toEntity(), id));

        return ResponseEntity.status(HttpStatus.CREATED).body(createPurchase);
    }
    
    
}
