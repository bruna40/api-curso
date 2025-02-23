package com.api.curso.api_curso.providers;

import org.springframework.stereotype.Service;

import com.api.curso.api_curso.module.users.model.entity.RoleEnum;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;


@Service
public class JWTUserProvider {


  private static final Logger logger = LoggerFactory.getLogger(JWTProvider.class);
  @Value("${security.token.secret.user}")
  private String secretKey;

  public DecodedJWT validateToken(String token) {
    token = token.replace("Bearer ", "");

    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    try {
      var tokenDecoded = JWT.require(algorithm)
          .build()
          .verify(token);

      return tokenDecoded;
    }  catch (JWTVerificationException e) {
        logger.error("Token inválido ou expirado", e);
        return null;
      } catch (Exception e) {
        logger.error("Erro ao validar o token", e);
        return null; 
      }
  }

  public String generateToken(String userId, RoleEnum roles) {
    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    var expiresIn = Instant.now().plus(Duration.ofDays(1));

    List<String> rolesList = List.of(roles.toString());
    return JWT.create()
        .withIssuer("auth0")
        .withClaim("userId", userId)
        .withClaim("roles", rolesList)
        .withExpiresAt(expiresIn)
        .sign(algorithm);
  }
}
