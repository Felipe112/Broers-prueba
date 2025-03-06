package com.broers.services;

import com.broers.model.EmailModel;

/**
 * Definiciones para el manejo de emails.
 *
 * @author Andrés F. Ceballos.
 * @since 2025-03-05.
 */
public interface EmailService {

  /**
   * Metodo encargado de envair un emails.
   *
   * @param email
   */
  void sendEmail(EmailModel email);

}
