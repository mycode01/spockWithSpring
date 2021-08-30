package com.example.spockdemo.services;

import com.example.spockdemo.common.CommonAssert;
import com.example.spockdemo.common.exceptions.CommonException;
import com.example.spockdemo.repo.UserRepository;
import com.example.spockdemo.services.EMailSender.Mail;
import com.example.spockdemo.services.exceptions.InvalidMailBodyException;
import com.example.spockdemo.services.exceptions.InvalidMarketUserIdException;
import com.example.spockdemo.services.exceptions.NotFoundUserException;
import com.example.spockdemo.services.exceptions.SendMailException;
import java.util.Optional;
import java.util.function.Predicate;
import org.hibernate.HibernateException;

public class StandardUserMailService implements UserMailService {

  final UserRepository userRepo;
  final EMailSender mailSender;

  public StandardUserMailService(UserRepository userRepo,
      EMailSender mailSender) {
    this.userRepo = userRepo;
    this.mailSender = mailSender;
  }

  @Override
  public void sendMailByMarketUserId(String marketUserId, String mailBody)
      throws CommonException, HibernateException {
    validate(marketUserId);
    validateMailBody(mailBody);

    final var user = Optional.ofNullable(userRepo.findUserByMarketUserId(marketUserId))
        .orElseThrow(NotFoundUserException::new);
    final Mail mail = new Mail(user.getEmail(), mailBody);

    sendMail(mail);
  }

  private void sendMail(Mail mail) throws SendMailException {
    try {
      mailSender.sendEMail(mail);
    } catch (Exception e) {
      throw new SendMailException();
    }
  }

  private void validate(String marketUserId) throws InvalidMarketUserIdException {
    //target exception class
    Class clazz = InvalidMarketUserIdException.class;

    CommonAssert.isEmpty(marketUserId, clazz);

    // 마켓 아이디에 숫자는 허용하지 않는다
    CommonAssert.isNotMatched(marketUserId, ".*[\\d].*", clazz);

    // 마켓 아이디는 5자 이상 12자 이하
    Predicate<String> p = s -> s.length() >= 5 && s.length() <= 12;
    CommonAssert.isTrue(p, marketUserId, clazz);
    // more
  }

  private void validateMailBody(String mailBody) throws InvalidMailBodyException {
    // 메일에는 비속어나 욕설이 포함되어서는 안됌
    String[] words = {"욕설", "비속어"};
    String filter = makeBodyFilter(words);
    CommonAssert.isNotMatched(mailBody, filter, InvalidMailBodyException.class);

    // 메일에는 글자수 제한이 있음
    CommonAssert.<String>isTrue(s -> s.length() < 1000, mailBody, InvalidMailBodyException.class);
  }

  private String makeBodyFilter(String... words) {
    StringBuilder regexp = new StringBuilder();
    boolean isFirst = true;
    regexp.append("(?=.*");
    for (String word : words) {
      if (!isFirst) {
        regexp.append("|");
      } else {
        isFirst = false;
      }
      regexp.append(word);
    }
    regexp.append(")");
    return regexp.toString();
  }
}
