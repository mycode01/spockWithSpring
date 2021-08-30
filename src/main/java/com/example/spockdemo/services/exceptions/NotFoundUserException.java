package com.example.spockdemo.services.exceptions;

import com.example.spockdemo.common.exceptions.CommonException;
import com.example.spockdemo.common.exceptions.ErrorCode;

public class NotFoundUserException extends CommonException {

  public NotFoundUserException() {
    super(ErrorCode.NOT_FOUND_USER);
  }
}
