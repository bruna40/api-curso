package com.api.curso.api_curso.exceptions;

public class UserNotFoundException extends NotFounException {
  public UserNotFoundException() {
    super("User not found");
  }
    
}
