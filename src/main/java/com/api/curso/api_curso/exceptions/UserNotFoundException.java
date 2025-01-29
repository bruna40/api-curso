package com.api.curso.api_curso.exceptions;

public class UserNotFoundException extends NotFoundException {
  public UserNotFoundException() {
    super("User not found");
  }
    
}
