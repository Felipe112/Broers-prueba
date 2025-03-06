package com.broers.repositories;

import com.broers.model.Token;
import com.broers.model.TokenType;
import com.broers.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de consultas relacionadas al modelo de token.
 *
 * @author Andrés F. Ceballos.
 * @since 2025-03-05.
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

  /**
   * Metodo que permite buscar un token con base en el valor y si ha sido usado.
   *
   * @param value
   * @param used
   *
   * @return
   */
  Optional<Token> findByValueAndUsed(String value, boolean used);

  /**
   * Metodo que permite una busqueda de los tokens relacionadaos a un usuario y el tipo de token.
   *
   * @param user
   * @param type
   *
   * @return
   */
  List<Token> findByUserAndTypeAndUsedFalse(User user, TokenType type);

}
