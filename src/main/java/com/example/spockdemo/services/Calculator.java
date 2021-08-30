package com.example.spockdemo.services;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator {


  public static Long calculate(long amount, float rate, RoundingMode mode){

    var res = BigDecimal.valueOf(amount).multiply(BigDecimal.valueOf(rate));
    res.setScale(0, mode);
    return res.longValue();

  }
}
