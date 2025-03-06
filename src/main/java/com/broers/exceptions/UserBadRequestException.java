package com.broers.exceptions;

public class UserBadRequestException extends RuntimeException {

  public UserBadRequestException(String message) {

    super(message);
  }

}
