package com.api.curso.api_curso.module.users.controller;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.api.curso.api_curso.exceptions.EmailAlreadyExistsException;
import com.api.curso.api_curso.exceptions.UserIdNotFound;
import com.api.curso.api_curso.module.cursos.model.dto.CreateCursoDTO;
import com.api.curso.api_curso.module.cursos.model.dto.CursoDTO;
import com.api.curso.api_curso.module.cursos.useCases.CursoUseCase;
import com.api.curso.api_curso.module.purchase.model.dto.CreatePurchaseDTO;
import com.api.curso.api_curso.module.purchase.model.dto.PurchaseDTO;
import com.api.curso.api_curso.module.purchase.useCase.PurchaseUseCase;
import com.api.curso.api_curso.module.users.model.dto.UpdateUserDTO;
import com.api.curso.api_curso.module.users.model.dto.UserDTO;
import com.api.curso.api_curso.module.users.model.entity.UserEntity;
import com.api.curso.api_curso.module.users.useCases.UserUseCase;
import io.swagger.v3.oas.annotations.Operation;
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSOR') or hasRole('USER')")
    @Operation(summary = "Criar curso", description = "Apenas pessoas com o perfil de administrador podem criar cursos")
    @ApiResponses({
    @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = CreateCursoDTO.class))
        }),
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<CursoDTO> createCurso(@PathVariable UUID id, @Valid @RequestBody CreateCursoDTO createCursoDTO) {
        CursoDTO curso  =  CursoDTO.fromEntity(cursoUseCase.createCurso(createCursoDTO.toEntity(), id));
        return  ResponseEntity.status(HttpStatus.CREATED).body(curso);
    } 


    @PostMapping("/{id}/purchase")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('PROFESSOR')")
    public ResponseEntity<PurchaseDTO> execute(@PathVariable UUID id, @RequestBody CreatePurchaseDTO purchaseEntity) {
        PurchaseDTO createPurchase = PurchaseDTO.fromEntity(purchaseUseCase.createPurchase(purchaseEntity.toEntity(), id));
        return ResponseEntity.status(HttpStatus.CREATED).body(createPurchase);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSOR') or hasRole('USER')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<UserEntity> updateUser(
        @Valid @PathVariable UUID id,
        @RequestBody UpdateUserDTO updateUserDTO,
        @AuthenticationPrincipal UserEntity user
    ){
        try {
             String userEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (userEmail == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            var userRole = userUseCase.getByEmail(userEmail);
            updateUserDTO.setId(id);
            userUseCase.updateUser(updateUserDTO, userEmail, userRole.getRole());
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    
}
