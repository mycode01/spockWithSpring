package com.example.spockdemo.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

  @ExceptionHandler({RuntimeException.class})
  public ResponseEntity globalExceptionHandler(){
    return ResponseEntity.status(500).body("System error");
  }
  // controller에서 에러를 모두 캐치하고 있기 때문에 실제로는 여기까지 안옴
}
