package com.example.spockdemo.controller.model;

import com.example.spockdemo.common.exceptions.CommonException;

public class FailureRes {
  String code;
  String message;

  public FailureRes(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public FailureRes(CommonException e){
    this.code = e.getErrorCode().getCode();
    this.message = e.getMessage();
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
