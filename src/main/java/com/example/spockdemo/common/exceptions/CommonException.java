package com.example.spockdemo.common.exceptions;

public class CommonException extends RuntimeException {
  final ErrorCode errorCode;

  public CommonException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }

}
