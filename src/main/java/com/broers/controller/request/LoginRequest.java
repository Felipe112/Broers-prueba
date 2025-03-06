package com.broers.controller.request;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Data;

@Data
public class LoginRequest implements Serializable {

  @NotBlank(message = "El email no puede estar vacío")
  private String email;

  @NotBlank(message = "El password no puede estar vacío")
  private String password;

}
