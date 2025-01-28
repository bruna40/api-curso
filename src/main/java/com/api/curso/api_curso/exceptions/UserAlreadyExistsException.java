package com.api.curso.api_curso.exceptions;

public class UserAlreadyExistsException extends NotFounException {
      
    public UserAlreadyExistsException(String message) {
        super(message);
    }
    
}
