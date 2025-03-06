package com.broers.common;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Clase configuraciones de token.
 *
 * @author Andrés F. Ceballos.
 * @since 2025-03-05.
 */
@Component
public class JsonWebToken {

  /// / / Propiedades /
  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private int expiration;

  ////
  //// MÉTODOS PÚBLICOS
  ////

  /**
   * Metodo empleado para generar un token.
   *
   * @param userDetails
   *
   * @return
   */
  public String generateToken(UserDetails userDetails) {

    return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(SignatureAlgorithm.HS512, secret).compact();
  }

  /**
   * Validaciópn de un token, comprueba si esta aun vigente.
   *
   * @param token
   *
   * @return
   */
  public Boolean validateToken(String token) {

    try {
      Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build()
          .parseSignedClaims(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

}
