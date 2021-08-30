package com.example.spockdemo.services.exceptions;

import com.example.spockdemo.common.exceptions.CommonException;
import com.example.spockdemo.common.exceptions.ErrorCode;

public class InvalidMarketUserIdException extends CommonException {

  public InvalidMarketUserIdException() {
    super(ErrorCode.INFORMAL_MARKET_ID);
  }
}
