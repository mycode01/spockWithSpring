package com.example.spockdemo.common;

import com.example.spockdemo.services.exceptions.InvalidMarketUserIdException;
import java.util.Objects;

public class CommonUtil {

  public static boolean isEmpty(String string){
    if (Objects.isNull(string) || string.length() == 0) {
      return true;
    }
    return false;
  }
}
