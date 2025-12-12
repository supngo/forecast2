package com.naturecode.forecast.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public final class AppSingleton {
  private static AppSingleton INSTANCE;
  private long tokenExpire = 0;
  private String weatherToken = null;
  private Map<Integer, String> weatherSymbols = new HashMap<Integer, String>();
  private String[] location = null;

  private AppSingleton() {
  }

  public static AppSingleton getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new AppSingleton();
    }
    return INSTANCE;
  }
}
