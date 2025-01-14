package com.api.curso.api_curso.providers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;


// esse arquivo serve para validar os token JWT
@Service
public class JWTProvider {

    private static final Logger logger = LoggerFactory.getLogger(JWTProvider.class);
    
    @Value("${security.token.secret}")
    private String secretKey;
  
    public DecodedJWT validateToken(String token) {
      token = token.replace("Bearer ", "");
  
      Algorithm algorithm = Algorithm.HMAC256(secretKey);
  
      try {
        var tokenDecoded = JWT.require(algorithm)
            .build()
            .verify(token);
  
        return tokenDecoded;
      } catch (TokenExpiredException ex) {
            logger.error("Token expirado", ex);
        } catch (JWTVerificationException ex) {
            logger.error("Token inválido", ex);
        } catch (Exception ex) {
            logger.error("Erro ao validar o token", ex);
        }
        return null;
    }
}
