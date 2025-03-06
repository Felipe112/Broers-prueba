package com.broers.services.impl;

import java.util.List;
import java.util.UUID;

import com.broers.exceptions.InvalidTokenException;
import com.broers.model.Token;
import com.broers.model.TokenType;
import com.broers.model.User;
import com.broers.repositories.TokenRepository;
import com.broers.services.TokenService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de gestionar los tokens.
 *
 * @author Andrés F. Ceballos.
 * @since 2025-03-05.
 */
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

  ////
  //// DEPENDENCIAS
  ////
  private final TokenRepository tokenRepository;

  ////
  //// PROPIEDADES
  ////
  private static final int REGISTRATION_TOKEN_EXPIRATION_HOURS = 24;

  private static final int PASSWORD_RESET_TOKEN_EXPIRATION_HOURS = 1;

  ////
  //// MÉTODOS PUBLICO
  ////

  @Override
  public Token generateToken(User user, TokenType type) {

    String tokenValue = UUID.randomUUID().toString();

    LocalDateTime expiryDate = calculateExpirationDate(type);

    Token token = new Token();
    token.setValue(tokenValue);
    token.setUser(user);
    token.setType(type);
    token.setExpiryDate(expiryDate);
    token.setUsed(false);

    return tokenRepository.save(token);
  }

  @Override
  public Token validateToken(String tokenValue, TokenType expectedType) {

    Token token = tokenRepository.findByValueAndUsed(tokenValue, false)
        .orElseThrow(() -> new InvalidTokenException("Token no encontrado o ya utilizado"));

    if (token.getType() != expectedType) {
      throw new InvalidTokenException("Tipo de token incorrecto");
    }

    if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
      throw new InvalidTokenException("Token expirado");
    }

    return token;
  }

  @Override
  public void saveToken(Token token) {

    tokenRepository.save(token);
  }

  @Override
  public void invalidatePreviousTokens(User user, TokenType type) {

    List<Token> activeTokens = tokenRepository.findByUserAndTypeAndUsedFalse(user, type);

    activeTokens.forEach(token -> {
      token.setUsed(true);
      tokenRepository.save(token);
    });
  }

  ////
  //// MÉTODOS PRIVADOS
  ////

  /**
   * Metodo usado para calcular la fecha de vencimiento de un token dependiendo el tipo de token.
   *
   * @param type
   *
   * @return
   */
  private LocalDateTime calculateExpirationDate(TokenType type) {

    return switch (type) {
      case REGISTRATION -> LocalDateTime.now().plusHours(REGISTRATION_TOKEN_EXPIRATION_HOURS);
      case PASSWORD_RESET -> LocalDateTime.now().plusHours(PASSWORD_RESET_TOKEN_EXPIRATION_HOURS);
    };
  }

}