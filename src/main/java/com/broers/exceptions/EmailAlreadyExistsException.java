package com.broers.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {

  public EmailAlreadyExistsException(String message) {

    super(message);
  }

}
