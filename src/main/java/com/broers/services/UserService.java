package com.broers.services;

import com.broers.controller.request.UserRegisterRequest;
import com.broers.model.User;

/**
 * Definiciones para el manejo de usuarios.
 *
 * @author Andrés F. Ceballos.
 * @since 2025-03-05.
 */
public interface UserService {

  /**
   * registro de usuarios.
   *
   * @param request
   *
   * @return
   */
  User registerUser(UserRegisterRequest request);

  /**
   * Activacion de un registro existente.
   *
   * @param token
   *
   * @return
   */
  User activateUser(String token);

  /**
   * Generación de token para restablecimiento de clave.
   *
   * @param email
   */
  void requestPasswordReset(String email);

  /**
   * Actualización de clave.
   *
   * @param token
   * @param newPassword
   */
  void updatePassword(String token, String newPassword);

}
