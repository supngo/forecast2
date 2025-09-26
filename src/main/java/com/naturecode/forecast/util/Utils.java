package com.naturecode.forecast.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.function.Function;

public class Utils {
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

	public static Map<String, Double> getAverage(JsonNode node, String type) {
		Map<String, List<Double>> avg = new LinkedHashMap<String, List<Double>>();
		List<String> keeper = new ArrayList<String>();
		List<Double> temp = new ArrayList<Double>();
		node.forEach(itr -> {
			String dow = getDayOfWeek(itr.get("date").asText());
			double min = itr.get("value").asDouble();
			if (keeper.contains(dow)) {
				temp.add(min);
			} else {
				if (!temp.isEmpty()) {
					String dayOfWeek = keeper.get(keeper.size() - 1);
					avg.put(dayOfWeek, temp.stream().collect(Collectors.toList()));
					temp.clear();
				}
				temp.add(min);
				keeper.add(dow);
			}
		});
		avg.put(keeper.get(keeper.size() - 1), temp);

		Map<String, Double> avgList = new LinkedHashMap<String, Double>();
		for (Map.Entry<String, List<Double>> entry : avg.entrySet()) {
			double value = 0;
			if (type.equals("min")) {
				value = Math.round(entry.getValue().stream().mapToDouble(a -> a).min().getAsDouble());
			} else if (type.equals("max")) {
				value = Math.round(entry.getValue().stream().mapToDouble(a -> a).max().getAsDouble());
			} else {
				Map<Double, Long> mostFreq = entry.getValue().stream()
						.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
				value = mostFreq.entrySet().stream().max((o1, o2) -> o1.getValue().compareTo(o2.getValue()))
						.map(Map.Entry::getKey).orElse(null);
			}
			avgList.put(entry.getKey(), value);
		}
		return avgList;
	}

	public static String getLatLngfromIp() {
		String result = "38.942838,-77.408236";
		// try {
		// LookupService cl;
		// File file =
		// InputStreamtoFile(Utils.class.getResourceAsStream("/GeoLiteCity.dat"));
		// cl = new LookupService(file, LookupService.GEOIP_MEMORY_CACHE |
		// LookupService.GEOIP_CHECK_CACHE);
		// URL whatismyip = new URL("http://checkip.amazonaws.com");
		// BufferedReader in = new BufferedReader(new
		// InputStreamReader(whatismyip.openStream()));
		// String ip = in.readLine();
		// Location location = cl.getLocation(ip);
		// result = location.latitude+","+location.longitude;
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		return result;
	}

	// private static File InputStreamtoFile(InputStream ins) throws IOException{
	// File file = new File("geo.dat");
	// if (file.exists())
	// file.delete();
	// OutputStream out=new FileOutputStream(new File("geo.dat"));
	// byte buf[]=new byte[1024];
	// int len;
	// while((len = ins.read(buf)) > 0)
	// out.write(buf,0,len);
	// out.close();
	// ins.close();

	// return file;
	// }
}