package com.broers.repositories;

import com.broers.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

/**
 * Repositorio de consultas relacionadas al modelo de usuarios.
 *
 * @author Andrés F. Ceballos.
 * @since 2025-03-05.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Metodo que permite saber si un email ya se encuentra registrado.
   *
   * @param email
   *
   * @return
   */
  boolean existsByEmail(String email);

  /**
   * Metodo que permite buscar un usuario con base en el email.
   *
   * @param email
   *
   * @return
   */
  Optional<User> findByEmail(String email);

}
