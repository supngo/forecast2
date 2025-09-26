package com.naturecode.forecast.util;

import java.util.Calendar;
import java.util.Date;

import com.ibm.icu.util.ChineseCalendar;

public class LunarDateExample {
  public static void main(String[] args) {
    // Current date
    Date today = new Date();

    // Convert to Chinese Lunar Calendar
    ChineseCalendar lunarCal = new ChineseCalendar();
    lunarCal.setTime(today);

    // Get lunar month and day
    int lunarMonth = lunarCal.get(Calendar.MONTH) + 1; // Months are 0-based
    int lunarDay = lunarCal.get(Calendar.DAY_OF_MONTH);

    System.out.println("Today's Lunar Date: Month " + lunarMonth + ", Day " + lunarDay);
  }
}
