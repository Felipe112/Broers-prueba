package com.broers.controller.request;

import com.broers.common.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Data;

@Data
public class UpdatePasswordRequest implements Serializable {

  @NotBlank(message = "El token no puede estar vacío")
  private String token;

  @NotBlank(message = "El password no puede estar vacío")
  @ValidPassword
  private String newPassword;

}
