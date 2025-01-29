package com.api.curso.api_curso.advice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.api.curso.api_curso.exceptions.EmailAlreadyExistsException;
import com.api.curso.api_curso.exceptions.InvalidaTokenException;
import com.api.curso.api_curso.exceptions.UserAlreadyExistsException;
import com.api.curso.api_curso.exceptions.UserIdNotFound;
import com.api.curso.api_curso.exceptions.UserNotFoundException;
import com.api.curso.api_curso.module.cursos.exceptions.CursoNotFoundException;
import com.api.curso.api_curso.module.cursos.exceptions.UnauthorizedActionException;

@ControllerAdvice
public class GlobalControllerAdvice {
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Usuário não encontrado: " + exception.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExists(UserAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body("Erro: O usuário já existe. " + exception.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExists(EmailAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Ocorreu um erro inesperado: " + exception.getMessage());
    }

    @ExceptionHandler(UserIdNotFound.class)
    public ResponseEntity<String> handleUserIdNotFound(UserIdNotFound exception) { 
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(exception.getMessage());
    }

    @ExceptionHandler(InvalidaTokenException.class)
    public ResponseEntity<String> handleInvalidaTokenException(InvalidaTokenException exception) { 
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(exception.getMessage());
    }


    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<String> handleUnauthorizedActionException(UnauthorizedActionException exception) { 
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(exception.getMessage());
    }

    @ExceptionHandler(CursoNotFoundException.class)
    public ResponseEntity<String> handleCursoNotFoundException(CursoNotFoundException exception) { 
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("O curso não foi encontrado" + exception.getMessage());
    }
    
}
