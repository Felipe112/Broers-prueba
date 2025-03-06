package com.broers.exceptions;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String message) {

    super(message);
  }

}
