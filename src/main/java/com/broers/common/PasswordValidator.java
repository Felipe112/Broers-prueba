package com.broers.common;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Clase configuraciones de password.
 *
 * @author Andrés F. Ceballos.
 * @since 2025-03-05.
 */
@Component
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

  @Value("${password.regex}")
  private String passwordRegex;

  ////
  //// MÉTODOS PÚBLICOS
  ////

  /**
   * Comprobación si el password es valido.
   * @param password
   * @param context
   * @return
   */
  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    if (password == null || password.isEmpty()) {
      return false;
    }
    return password.matches(passwordRegex);
  }
}

