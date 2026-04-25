package com.naturecode.forecast.credential;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.json.JSONArray;
import org.json.JSONObject;
import com.naturecode.forecast.model.Forecast;
import com.naturecode.forecast.model.ForecastFull;
import com.naturecode.forecast.util.Utils;

@Slf4j
@Service
public class Credential {

	@Value("${weather.api.key:default}")
	private String apiKey;

	private final RestTemplate restTemplate;

	public Credential() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(5000);
		factory.setReadTimeout(10000);
		this.restTemplate = new RestTemplate(factory);
	}

	public ForecastFull getForecastFromWeatherApi(String latlng) {
		ForecastFull result = new ForecastFull();
		String weatherApiUrl = "http://api.weatherapi.com/v1/forecast.json?key=" + apiKey + "&q=" + latlng + "&days=5&aqi=no&alerts=no";
		ResponseEntity<String> response = restTemplate.getForEntity(weatherApiUrl, String.class);
		try {
			JSONObject root = new JSONObject(response.getBody());
			result.setCity(root.getJSONObject("location").getString("name"));
			result.setRegion(root.getJSONObject("location").getString("region"));
			result.setCountry(root.getJSONObject("location").getString("country"));

			JSONObject current = root.getJSONObject("current");
			result.setCurrentTemp((int) current.getDouble("temp_f"));

			String isDay = current.getInt("is_day") == 0 ? "night" : "day";
			int code = current.getJSONObject("condition").getInt("code");
			result.setIcon(Utils.getIcon(code, isDay));

			JSONArray forecastDays = root.getJSONObject("forecast").getJSONArray("forecastday");
			JSONObject todayDay = forecastDays.getJSONObject(0).getJSONObject("day");
			result.setHiTemp((int) todayDay.getDouble("maxtemp_f"));
			result.setLoTemp((int) todayDay.getDouble("mintemp_f"));

			List<Forecast> days = new ArrayList<>();
			for (int i = 1; i < forecastDays.length(); i++) {
				JSONObject entry = forecastDays.getJSONObject(i);
				JSONObject dayObj = entry.getJSONObject("day");
				Forecast day = new Forecast();
				day.setDayOfWeek(Utils.getDayOfWeek(entry.getString("date")));
				day.setHiTemp((int) dayObj.getDouble("maxtemp_f"));
				day.setLoTemp((int) dayObj.getDouble("mintemp_f"));
				day.setIcon(Utils.getIcon(dayObj.getJSONObject("condition").getInt("code"), "day"));
				days.add(day);
			}
			result.setForecastList(days);

		} catch (Exception e) {
			log.error("Failed to fetch forecast from weather API", e);
		}
		return result;
	}
}
