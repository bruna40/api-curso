package com.api.curso.api_curso.exceptions;

public class InvalidaTokenException extends NotFoundException {
    public InvalidaTokenException() {
        super("Token inválido ou ausente!");
    }
}
