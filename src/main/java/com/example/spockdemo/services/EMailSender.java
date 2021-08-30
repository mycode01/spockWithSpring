package com.example.spockdemo.services;

public interface EMailSender {

  void sendEMail(Mail mail);


  public static class Mail {
    String receiver;
    String body;

    public Mail(String receiver, String body) {
      this.receiver = receiver;
      this.body = body;
    }

    public String getReceiver() {
      return receiver;
    }

    public String getBody() {
      return body;
    }
  }
}
