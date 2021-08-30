package com.example.spockdemo.common.exceptions;

public class UnknownException extends CommonException {

  public UnknownException() {
    super(ErrorCode.SYSTEM_ERROR);
  }
}
