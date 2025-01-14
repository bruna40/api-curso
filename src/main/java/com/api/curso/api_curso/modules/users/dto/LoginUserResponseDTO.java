package com.api.curso.api_curso.modules.users.dto;

import com.api.curso.api_curso.modules.users.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserResponseDTO {
  private String accessToken;
  private Role role;
}
