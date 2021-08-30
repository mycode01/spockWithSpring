package com.example.spockdemo.controller.exceptions;

import com.example.spockdemo.common.exceptions.CommonException;
import com.example.spockdemo.common.exceptions.ErrorCode;

public class InvalidApiParameterException extends CommonException {

  private String message;

  public InvalidApiParameterException(String message) {
    super(ErrorCode.WRONG_PARAMETER);
    this.message = message;
  }

  @Override
  public String getMessage() {
    return super.getMessage() + " : " + message;
  }
}
