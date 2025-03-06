package com.broers.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

  public static final String SUCCESS_RESPONSE = "Proceso exitoso";

  public static final String ERROR_RESPONSE = "Error process";

  public static final String EXPIRED_TOKEN = "El token a vencido";

  public static final String PASSWORD_DEFAULT = "Broers1234";

  public static final String SUBJECT_ACTIVE = "Activa tu cuenta";

  public static final String TEXT_ACTIVE =
      "Haz clic en el enlace para activar tu cuenta: http://localhost:8081/api/v1/users/activate?token=";

  public static final String SUBJECT_RESET = "Restablece tu contraseña";

  public static final String TEXT_RESET =
      "Haz clic en el enlace para restablecer tu contraseña: http://localhost:8081/api/v1/users/reset?token=";

  public static final String LINK_RESET = "Enlace de recuperación enviado";

  public static final String UPDATE_PASS_MESSAGE = "Contraseña actualizada exitosamente";

}
