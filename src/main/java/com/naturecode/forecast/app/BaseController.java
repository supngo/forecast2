package com.naturecode.forecast.app;

import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naturecode.forecast.credential.Credential;
import com.naturecode.forecast.model.ForecastFull;
import com.naturecode.forecast.util.GpsCache;
import com.naturecode.forecast.util.Utils;

@Slf4j
@Controller
public class BaseController {
	private static final String VIEW_INDEX = "index";

	private final GpsCache cache;
	private final Credential credential;

	public BaseController(GpsCache cache, Credential credential) {
		this.cache = cache;
		this.credential = credential;
	}

	@Value("${latitude}")
	private String latitude;

	@Value("${longitude}")
	private String longitude;

	@RequestMapping(value = "/forecast", method = RequestMethod.GET)
	public String weather(ModelMap model, HttpServletRequest request) {
		String latlng = latitude + "," + longitude;
		try {
			GpsCache.GpsFix fix = cache.getLatest();
			if(fix != null) {
				latlng = fix.latitude() + "," + fix.longitude();
				log.info("Getting Lat Lng from GPS: {}", latlng);
			}
			ForecastFull result = credential.getForecastFromWeatherApi(latlng);
			model.addAttribute("tempString", result.getCurrentTemp());
			model.addAttribute("tempMin", result.getLoTemp());
			model.addAttribute("tempMax", result.getHiTemp());
			model.addAttribute("wIcon", result.getIcon());
			model.addAttribute("country", "USA");
			model.addAttribute("state", result.getRegion());
			model.addAttribute("city", result.getCity());
			model.addAttribute("forecast", result.getForecastList());
		} catch (Exception e) {
			log.error("Exception in weather", e);
		}

		// Getting Lunar Date
		String lunarDate = Utils.getLunarDate();
		model.addAttribute("lunarDate", lunarDate);		

		// Spring uses InternalResourceViewResolver and return back index.jsp
		return VIEW_INDEX;
	}
}