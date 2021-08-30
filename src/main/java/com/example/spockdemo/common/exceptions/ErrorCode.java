package com.example.spockdemo.common.exceptions;

import java.util.Map;

public enum ErrorCode {


  WRONG_PARAMETER(400, "P010", "Invalid parameter(s)."),
  INFORMAL_MARKET_ID(400, "P011", "Invalid marketUserId for the ID rule."),
  INVALID_EMAIL_TEXT(400, "P012", "Inappropriate words for mail sending."),
  NOT_FOUND_USER(404, "U404", "Not found user."),
  SENDMAIL_FAILURE(500, "M001", "Exception thrown while sending mail."),

  SYSTEM_ERROR(500, "S000", "Somethings wrong.");

  private final String code;
  private final String message;
  private int status;

  ErrorCode(final int status, final String code, final String message) {
    this.status = status;
    this.message = message;
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public int getStatus() {
    return status;
  }

}