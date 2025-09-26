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

  public void setupWeatherSymbols() {
    weatherSymbols.put(0, "partly_cloudy_night");

    weatherSymbols.put(1, "clear_day");
    weatherSymbols.put(2, "partly_cloudy_day");
    weatherSymbols.put(3, "partly_cloudy_day");
    weatherSymbols.put(4, "cloudy");
    weatherSymbols.put(5, "rain");
    weatherSymbols.put(6, "rain");
    weatherSymbols.put(7, "snow");
    weatherSymbols.put(8, "rain");
    weatherSymbols.put(9, "snow");
    weatherSymbols.put(10, "sleet");
    weatherSymbols.put(11, "fog");
    weatherSymbols.put(12, "fog");
    weatherSymbols.put(13, "rain");
    weatherSymbols.put(14, "rain");
    weatherSymbols.put(15, "rain");
    weatherSymbols.put(16, "win");

    weatherSymbols.put(101, "clear_night");
    weatherSymbols.put(102, "partly_cloudy_night");
    weatherSymbols.put(103, "partly_cloudy_night");
    weatherSymbols.put(104, "cloudy");
    weatherSymbols.put(105, "rain");
    weatherSymbols.put(106, "rain");
    weatherSymbols.put(107, "snow");
    weatherSymbols.put(108, "rain");
    weatherSymbols.put(109, "snow");
    weatherSymbols.put(110, "sleet");
    weatherSymbols.put(111, "fog");
    weatherSymbols.put(112, "fog");
    weatherSymbols.put(113, "rain");
    weatherSymbols.put(114, "rain");
    weatherSymbols.put(115, "rain");
    weatherSymbols.put(116, "win");
  }
}
