package com.broers.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import lombok.Data;

@Data
public class UserRegisterRequest implements Serializable {

  @NotBlank(message = "El nombre no puede estar vacío")
  private String name;

  @NotBlank(message = "El correo no puede estar vacío")
  @Pattern(regexp = "^[a-zA-Z0-9]{1,50}@[a-zA-Z0-9]{1,50}\\.[a-zA-Z]{2,}$",
      message = "El correo debe tener un usuario y dominio alfanuméricos (máx. 50 caracteres)")
  private String email;

}
