package com.example.spockdemo;

import com.example.spockdemo.repo.UserRepository;
import com.example.spockdemo.services.EMailSender;
import com.example.spockdemo.services.StandardUserMailService;
import com.example.spockdemo.services.UserMailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class SpockDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpockDemoApplication.class, args);
  }


  @Bean
  UserMailService userMailService(UserRepository userRepo) {
    EMailSender mailSender = new EMailSender() {
      @Override
      public void sendEMail(Mail mail) {
        System.out.println("Mail send : " + mail.getReceiver() + "::" + mail.getBody());
      }
    };
    return new StandardUserMailService(userRepo, mailSender);
  }
}
