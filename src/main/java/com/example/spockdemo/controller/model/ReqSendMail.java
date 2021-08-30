package com.example.spockdemo.controller.model;

import javax.validation.constraints.NotBlank;

public class ReqSendMail {
  @NotBlank
  String marketUserId;
  @NotBlank
  String mailBody;

  public void setMarketUserId(String marketUserId) {
    this.marketUserId = marketUserId;
  }

  public void setMailBody(String mailBody) {
    this.mailBody = mailBody;
  }

  public String getMarketUserId() {
    return marketUserId;
  }

  public String getMailBody() {
    return mailBody;
  }
}
