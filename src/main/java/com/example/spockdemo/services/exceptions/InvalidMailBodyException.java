package com.example.spockdemo.services.exceptions;

import com.example.spockdemo.common.exceptions.CommonException;
import com.example.spockdemo.common.exceptions.ErrorCode;

public class InvalidMailBodyException extends CommonException {

  public InvalidMailBodyException() {
    super(ErrorCode.INVALID_EMAIL_TEXT);
  }
}
