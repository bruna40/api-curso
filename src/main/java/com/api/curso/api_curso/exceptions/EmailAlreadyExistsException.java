package com.api.curso.api_curso.exceptions;

public class EmailAlreadyExistsException  extends NotFoundException {
    
    public EmailAlreadyExistsException() {
        super("O e-mail informado já está cadastrado.");
    }
}
