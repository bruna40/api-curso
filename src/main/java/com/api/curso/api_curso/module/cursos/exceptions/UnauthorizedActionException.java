package com.api.curso.api_curso.module.cursos.exceptions;

import com.api.curso.api_curso.exceptions.NotFoundException;

public class UnauthorizedActionException extends NotFoundException{
    public  UnauthorizedActionException(String message) {
        super(message);
    }
}
