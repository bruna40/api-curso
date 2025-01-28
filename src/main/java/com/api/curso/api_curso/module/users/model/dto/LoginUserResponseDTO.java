package com.api.curso.api_curso.module.users.model.dto;

import com.api.curso.api_curso.module.users.model.entity.RoleEnum;

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
  private RoleEnum role;
}
