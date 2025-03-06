package com.broers.controller.request;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Data;

@Data
public class ActivateUserRequest implements Serializable {

  @NotBlank(message = "El token no puede estar vacío")
  private String token;

  @NotBlank(message = "El password no puede estar vacío")
  private String Password;

}
