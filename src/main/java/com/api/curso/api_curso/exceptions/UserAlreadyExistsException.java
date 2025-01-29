package com.api.curso.api_curso.exceptions;

public class UserAlreadyExistsException extends NotFoundException {
      
    public UserAlreadyExistsException(String message) {
        super(message);
    }
    
}
