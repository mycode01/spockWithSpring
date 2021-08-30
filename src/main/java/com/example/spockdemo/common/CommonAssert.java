package com.example.spockdemo.common;

import com.example.spockdemo.common.exceptions.CommonException;
import com.example.spockdemo.common.exceptions.UnknownException;
import com.example.spockdemo.services.exceptions.InvalidMarketUserIdException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import org.springframework.util.Assert;

public class CommonAssert extends Assert {

  private static final String NUMBER_ALPHABET_PATTERN = "^[a-zA-Z\\d]+$";
  private static final String NUMERIC_PATTERN = "^[\\+\\-]{0,1}[\\d]+$";


  public static void isMatched(String object, String pattern,
      Class<? extends CommonException> exceptionClass) {
    if (Objects.isNull(object) || object.length() == 0) {
      throwException(exceptionClass);
    }

    if (!isMatched(object, pattern)) {
      throwException(exceptionClass);
    }
  }

  public static void isNotMatched(String object, String pattern,
      Class<? extends CommonException> exceptionClass) {
    if (Objects.isNull(object) || object.length() == 0) {
      return;
    }

    if (isMatched(object, pattern)) {
      throwException(exceptionClass);
    }
  }

  private static boolean isMatched(String object, String pattern) {
    return Pattern.compile(pattern).matcher(object).find();
//    return object.matches(pattern); // 일부 매치인 경우 동작 않음
  }

  public static <T> void isTrue(Predicate<T> predicate, T object,
      Class<? extends CommonException> exceptionClass) {
    if (!predicate.test(object)) {
      throwException(exceptionClass);
    }
  }

  public static void isEmpty(String object, Class<? extends CommonException> exceptionClass) {
    if (CommonUtil.isEmpty(object)) {
      throwException(exceptionClass);
    }
  }


  private static void throwException(final Class<? extends CommonException> exceptionClass) {
    try {
      throw exceptionClass.getDeclaredConstructor().newInstance();
    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
      e.printStackTrace();
      throw new UnknownException();
    }
  }
}
