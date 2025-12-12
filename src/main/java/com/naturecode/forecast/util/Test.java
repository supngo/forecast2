package com.naturecode.forecast.util;

// import java.text.DateFormat;
// import java.text.ParseException;
// import java.text.SimpleDateFormat;
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.time.Month;
// import java.time.ZoneId;
// import java.time.ZonedDateTime;
// import java.time.format.DateTimeFormatter;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.Base64;
// import java.util.Date;
import java.util.List;
// import java.util.Map;
// import java.util.function.Function;
// import java.util.stream.Collectors;

// import org.json.JSONArray;
// import org.springframework.format.annotation.DateTimeFormat;

import com.naturecode.forecast.credential.Credential;
import com.naturecode.forecast.model.Forecast;

public class Test {

	public static void main(String[] args) {
		// String[] location = new String[4];
		// try{
		// String latlng = Utils.getLatLngfromIp();
		// System.out.println("latlng: " + latlng);
		// location = Utils.getLocationfromLatLng(latlng);
		// System.out.println("location: " + location[0] + location[2]);
		// } catch(Exception e){
		// e.printStackTrace();
		// }

		// ArrayList <Forecast> arrayList = new ArrayList<Forecast>();
		// arrayList.add(new Forecast("Wed", 62, 40, "clear"));
		// arrayList.add(new Forecast("Thu", 65, 38, "cloud"));
		// JSONArray jsArray2 = new JSONArray(arrayList);
		// System.out.println(jsArray2);

		// String token = Credential.getMeteomaticsToken();
		// String token =
		// "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJ2IjoxLCJ1c2VyIjoiaG9tZV9uZ28iLCJpc3MiOiJsb2dpbi5tZXRlb21hdGljcy5jb20iLCJleHAiOjE2ODA0MTI1MjAsInN1YiI6ImFjY2VzcyJ9.WWEkESyr7RAOqiwrFb-TP0_Uq2QDXZlciA4qz8Ui1eJuRE8m60lfp9Qg_Kzlg7ie9Mc8vWcJWmf9gFUMBBh_hg";
		// String decodedString = new String(Base64.getMimeDecoder().decode(token));
		// int head = decodedString.lastIndexOf("exp") + 5;
		// int tail = decodedString.lastIndexOf("sub") - 2;
		// System.out.println("--> head: " + head);
		// System.out.println("--> tail: " + tail);
		// System.out.println("--> decodedString: " + decodedString.substring(head,
		// tail));

		// LocalDateTime ldt = LocalDateTime.now();
		// ZonedDateTime ldtZoned = ldt.atZone(ZoneId.systemDefault());
		// ZonedDateTime utcZoned = ldtZoned.withZoneSameInstant(ZoneId.of("UTC"));
		// System.out.println(utcZoned.toLocalDateTime().toString().split("\\.")[0] +
		// "Z");

		// String temp = Credential.getCurrentTemp("38.942838,-77.408236");
		// System.out.println("--> temp: " + temp);

		// String test = Credential.getUTCDate(1);
		// System.out.println("--> test: " + test);

		// List<Integer> result = Credential.getCurrentTemp("38.942838,-77.408236");
		// result.stream().forEach(itr -> {
		// System.out.println(itr);
		// });

		// List<Integer> result = Credential.getCurrentMinMax("38.942838,-77.408236");
		// result.stream().forEach(itr -> {
		// System.out.println(itr);
		// });

		// String location[] = Credential.getLocationfromLatLng("38.942838,-77.408236");
		// System.out.println(location[0]);
		// System.out.println(location[1]);
		// System.out.println(location[2]);

		// String dateStr = "2023-04-03T04:00:00";
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// try {
		// Date date = sdf.parse(dateStr.split("T")[0]);
		// DateFormat format2 = new SimpleDateFormat("EEE");
		// String finalDay = format2.format(date);
		// System.out.println(finalDay);
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }


		// result.stream().forEach(itr -> {
		// System.out.println(itr);
		// });

		// List<String> g = Arrays.asList("geeks", "for", "geeks");
		// Map<String, Long> result =
		// g.stream().collect(Collectors.groupingBy(Function.identity(),
		// Collectors.counting()));
		// System.out.println(result);
	}

}
