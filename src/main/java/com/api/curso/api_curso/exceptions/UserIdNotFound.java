package com.api.curso.api_curso.exceptions;

public class UserIdNotFound extends NotFoundException {
    public UserIdNotFound() {
        super("Id não encontrado!");
    }
}
