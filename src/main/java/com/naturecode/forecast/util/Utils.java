package com.naturecode.forecast.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Utils {
	private static final Map<Integer, Map<String, String>> ICON_MAP = new HashMap<>();
	static {
		// --- Clear / Cloudy ---
		putIcon(1000, "day", "clear_day");
		putIcon(1000, "night", "clear_night");

		putIcon(1003, "day", "partly_cloudy_day");
		putIcon(1003, "night", "partly_cloudy_night");

		putIcon(1006, "day", "partly_cloudy_day");
		putIcon(1006, "night", "partly_cloudy_night");

		putIcon(1009, "day", "cloudy");
		putIcon(1009, "night", "cloudy");

		putIcon(1030, "day", "fog");
		putIcon(1030, "night", "fog");

		// --- Rain / Drizzle ---
		putIcon(1063, "day", "rain");
		putIcon(1063, "night", "rain");

		putIcon(1072, "day", "rain_snow_shower_day");
		putIcon(1072, "night", "rain_snow_shower_night");

		putIcon(1150, "day", "rain");
		putIcon(1150, "night", "rain");

		putIcon(1153, "day", "rain");
		putIcon(1153, "night", "rain");

		putIcon(1168, "day", "rain");
		putIcon(1168, "night", "rain");

		putIcon(1171, "day", "rain");
		putIcon(1171, "night", "rain");

		putIcon(1180, "day", "rain");
		putIcon(1180, "night", "rain");

		putIcon(1183, "day", "rain");
		putIcon(1183, "night", "rain");

		putIcon(1186, "day", "rain");
		putIcon(1186, "night", "rain");

		putIcon(1189, "day", "rain");
		putIcon(1189, "night", "rain");

		putIcon(1192, "day", "showers_day");
		putIcon(1192, "night", "showers_night");

		putIcon(1195, "day", "showers_day");
		putIcon(1195, "night", "showers_night");

		putIcon(1198, "day", "rain");
		putIcon(1198, "night", "rain");

		putIcon(1201, "day", "showers_day");
		putIcon(1201, "night", "showers_night");

		putIcon(1240, "day", "showers_day");
		putIcon(1240, "night", "showers_night");

		putIcon(1243, "day", "rain");
		putIcon(1243, "night", "rain");

		putIcon(1246, "day", "rain");
		putIcon(1246, "night", "rain");

		// --- Snow / Sleet ---
		putIcon(1066, "day", "snow");
		putIcon(1066, "night", "snow");

		putIcon(1069, "day", "sleet");
		putIcon(1069, "night", "sleet");

		putIcon(1114, "day", "snow");
		putIcon(1114, "night", "snow");

		putIcon(1117, "day", "snow");
		putIcon(1117, "night", "snow");

		putIcon(1204, "day", "sleet");
		putIcon(1204, "night", "sleet");

		putIcon(1207, "day", "sleet");
		putIcon(1207, "night", "sleet");

		putIcon(1210, "day", "snow");
		putIcon(1210, "night", "snow");

		putIcon(1213, "day", "snow");
		putIcon(1213, "night", "snow");

		putIcon(1216, "day", "snow");
		putIcon(1216, "night", "snow");

		putIcon(1219, "day", "snow");
		putIcon(1219, "night", "snow");

		putIcon(1222, "day", "snow");
		putIcon(1222, "night", "snow");

		putIcon(1225, "day", "snow");
		putIcon(1225, "night", "snow");

		putIcon(1249, "day", "sleet");
		putIcon(1249, "night", "sleet");

		putIcon(1252, "day", "sleet");
		putIcon(1252, "night", "sleet");

		putIcon(1255, "day", "snow");
		putIcon(1255, "night", "snow");

		putIcon(1258, "day", "snow");
		putIcon(1258, "night", "snow");

		// --- Ice / Hail ---
		putIcon(1237, "day", "hail");
		putIcon(1237, "night", "hail");

		putIcon(1261, "day", "hail");
		putIcon(1261, "night", "hail");

		putIcon(1264, "day", "hail");
		putIcon(1264, "night", "hail");

		// --- Thunder ---
		putIcon(1087, "day", "thunder");
		putIcon(1087, "night", "thunder");

		putIcon(1273, "day", "thunder_rain");
		putIcon(1273, "night", "thunder_rain");

		putIcon(1276, "day", "thunder_rain");
		putIcon(1276, "night", "thunder_rain");

		putIcon(1279, "day", "thunder");
		putIcon(1279, "night", "thunder");

		putIcon(1282, "day", "thunder_showers_day");
		putIcon(1282, "night", "thunder_showers_night");

		// --- Fog ---
		putIcon(1135, "day", "fog");
		putIcon(1135, "night", "fog");

		putIcon(1147, "day", "fog");
		putIcon(1147, "night", "fog");
	}

	private static void putIcon(int code, String timeOfDay, String icon) {
		ICON_MAP.computeIfAbsent(code, k -> new HashMap<>()).put(timeOfDay.toLowerCase(), icon);
	}

	public static String getIcon(int code, String timeOfDay) {
		Map<String, String> timeMap = ICON_MAP.get(code);
		if (timeMap == null) {
			return null; // or throw exception
		}
		return timeMap.get(timeOfDay.toLowerCase()).toUpperCase();
	}

	public static String getUTCDateTime() {
		String result = null;
		LocalDateTime ldt = LocalDateTime.now();
		ZonedDateTime ldtZoned = ldt.atZone(ZoneId.systemDefault());
		ZonedDateTime utcZoned = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));
		result = utcZoned.toLocalDateTime().toString().split("\\.")[0] + "Z";
		return result;
	}

	public static String getUTCDate(int offset) {
		String result = null;
		LocalDate currentdate = LocalDate.now();
		LocalDate to = currentdate.plusDays(offset);
		int currentDay = to.getDayOfMonth();
		int currentYear = to.getYear();
		int currentMonth = to.getMonth().getValue();
		LocalDateTime ldt;
		if (offset == 5) {
			ldt = LocalDateTime.of(currentYear, currentMonth, currentDay, 18, 0, 0);
		} else {
			ldt = LocalDateTime.of(currentYear, currentMonth, currentDay, 0, 0, 0);
		}
		ZonedDateTime ldtZoned = ldt.atZone(ZoneId.systemDefault());
		ZonedDateTime utcZoned = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));
		result = utcZoned.toLocalDateTime().toString().split("\\.")[0] + "Z";
		return result;
	}

	public static String getDayOfWeek(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sdf.parse(dateStr.split("T")[0]);
			DateFormat format2 = new SimpleDateFormat("EEE");
			return format2.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "NA";
	}

	public static String getLatLngfromIp() {
		String result = "38.942838,-77.408236";
		return result;
	}
}