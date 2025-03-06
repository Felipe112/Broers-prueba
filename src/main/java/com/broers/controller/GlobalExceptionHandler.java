package com.broers.controller;

import com.broers.common.Constants;
import com.broers.controller.response.BasicResponse;
import com.broers.exceptions.EmailAlreadyExistsException;
import com.broers.exceptions.InvalidTokenException;
import com.broers.exceptions.UserBadRequestException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({UserBadRequestException.class})
  public ResponseEntity<Object> handleStudentNotFoundException(UserBadRequestException exception) {

    BasicResponse<String> response = new BasicResponse<>(exception.getMessage());
    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler({EmailAlreadyExistsException.class})
  public ResponseEntity<Object> handleStudentNotFoundException(
      EmailAlreadyExistsException exception) {

    BasicResponse<String> response = new BasicResponse<>(exception.getMessage());
    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler({InvalidTokenException.class})
  public ResponseEntity<Object> handleStudentAlreadyExistsException(
      InvalidTokenException exception) {

    BasicResponse<String> response = new BasicResponse<>(exception.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
  }

  @ExceptionHandler({RuntimeException.class})
  public ResponseEntity<BasicResponse<String>> handleRuntimeException(RuntimeException exception) {

    BasicResponse<String> response = new BasicResponse<>(Constants.ERROR_RESPONSE);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }

}