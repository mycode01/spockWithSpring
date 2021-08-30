package com.example.spockdemo.controller;

import com.example.spockdemo.common.exceptions.CommonException;
import com.example.spockdemo.common.exceptions.ErrorCode;
import com.example.spockdemo.controller.exceptions.InvalidApiParameterException;
import com.example.spockdemo.controller.model.FailureRes;
import com.example.spockdemo.controller.model.ReqSendMail;
import com.example.spockdemo.controller.model.ResSendMail;
import com.example.spockdemo.services.UserMailService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

  final UserMailService userMailService;

  public DefaultController(UserMailService userMailService) {
    this.userMailService = userMailService;
  }

  @PostMapping("mail")
  public ResponseEntity sendMail(@Valid @RequestBody ReqSendMail req) {
    userMailService.sendMailByMarketUserId(req.getMarketUserId(), req.getMailBody());

    return ResponseEntity.ok(new ResSendMail(req.getMarketUserId(), req.getMailBody()));
  }

  @ExceptionHandler // ExceptionHandler가 여러개라도 Specific 한 타입을 지정한 녀석이 우선인듯
  public ResponseEntity exceptionHandler(Throwable t) {
    if (t instanceof CommonException) {
      return ResponseEntity.status(((CommonException) t).getErrorCode().getStatus())
          .body(new FailureRes((CommonException) t));
    }
    return ResponseEntity.internalServerError().build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity processValidationError(MethodArgumentNotValidException t) {
    var msg = t.getBindingResult().getAllErrors().stream().map(e->e.getDefaultMessage()).collect(Collectors.toList()).toString();
    return ResponseEntity.badRequest().body(new FailureRes(new InvalidApiParameterException(msg)));
  } // error response가 어설프게 작성되어 메시지만 넣어둠

}
