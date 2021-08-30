package com.example.spockdemo.controller.model;

public class ResSendMail {
  final String marketUserId;
  final String mailBody;

  public ResSendMail(String marketUserId, String mailBody) {
    this.marketUserId = marketUserId;
    this.mailBody = mailBody;
  }

  public String getMarketUserId() {
    return marketUserId;
  }

  public String getMailBody() {
    return mailBody;
  }
}
