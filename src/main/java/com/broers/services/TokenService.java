package com.broers.services;

import com.broers.model.Token;
import com.broers.model.TokenType;
import com.broers.model.User;

/**
 * Definiciones para el manejo de tokens.
 *
 * @author Andrés F. Ceballos.
 * @since 2025-03-05.
 */
public interface TokenService {

  /**
   * Generador de tokens.
   *
   * @param user
   * @param type
   *
   * @return
   */
  Token generateToken(User user, TokenType type);

  /**
   * Validación de tokens, activos y vigentes.
   *
   * @param tokenValue
   * @param expectedType
   *
   * @return
   */
  Token validateToken(String tokenValue, TokenType expectedType);

  /**
   * Metodo encargado de almacenar los tokens de los usuarios.
   *
   * @param token
   */
  void saveToken(Token token);

  /**
   * Invalidar los tokens previos al que se este consultando.
   *
   * @param user
   * @param type
   */
  void invalidatePreviousTokens(User user, TokenType type);

}
