package com.broers.services.impl;

import com.broers.model.EmailModel;
import com.broers.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de gestionar la conexion con los EMAILS.
 *
 * @author Andrés F. Ceballos.
 * @since 2025-03-05.
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender mailSender;

  /**
   * Metodo que permite enviar un email.
   *
   * @param email
   */
  @Override
  public void sendEmail(EmailModel email) {

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(email.getEmail());
    message.setSubject(email.getSubject());
    message.setText(email.getText() + email.getToken());
    mailSender.send(message);
  }

}