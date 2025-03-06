package com.broers.controller.response;

import com.broers.common.Constants;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasicResponse<T> implements Serializable {

  private String mensaje;

  private LocalDate date;

  private T data;

  public BasicResponse(T data) {

    this.mensaje = Constants.SUCCESS_RESPONSE;
    this.date = LocalDate.now();
    this.data = data;
  }

  public BasicResponse(String message) {

    this.mensaje = message;
    this.date = LocalDate.now();
    this.data = null;
  }

  public BasicResponse(String message, T data) {

    this.mensaje = message;
    this.date = LocalDate.now();
    this.data = data;
  }

}
