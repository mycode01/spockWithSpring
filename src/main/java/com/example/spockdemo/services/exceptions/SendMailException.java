package com.example.spockdemo.services.exceptions;

import com.example.spockdemo.common.exceptions.CommonException;
import com.example.spockdemo.common.exceptions.ErrorCode;

public class SendMailException extends CommonException {

  public SendMailException() {
    super(ErrorCode.SENDMAIL_FAILURE);
  }
}
