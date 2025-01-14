package com.api.curso.api_curso.providers;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

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
}
