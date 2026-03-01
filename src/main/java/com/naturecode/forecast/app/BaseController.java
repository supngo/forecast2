package com.naturecode.forecast.app;

import java.util.Calendar;
import java.util.Date;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ibm.icu.util.ChineseCalendar;
import com.naturecode.forecast.credential.Credential;
import com.naturecode.forecast.model.ForecastFull;
import com.naturecode.forecast.util.GpsCache;
import com.naturecode.forecast.util.Utils;

@Controller
public class BaseController {
	private static final String VIEW_INDEX = "index";
	private static final String VIEW_COOLEST = "coolest";
	private static final String VIEW_BLANK = "blank";

	private final GpsCache cache;
  public BaseController(GpsCache cache) {
    this.cache = cache;
  }

	@Value("${latitude}")
	private String latitude;

	@Value("${longitude}")
	private String longitude;

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BaseController.class);

	@RequestMapping(value = "/forecast", method = RequestMethod.GET)
	public String weather(ModelMap model, HttpServletRequest request) {
		String latlng = Utils.getLatLngfromIp();
		try {
			GpsCache.GpsFix fix = cache.getLatest();
			if(fix != null) {
				latlng = fix.latitude() + "," + fix.longitude();
				logger.info("Getting Lat Lng from GPS: {}", latlng);
			}
			ForecastFull result = Credential.getForecastFromWeatherApi(latlng);
			model.addAttribute("tempString", result.getCurrentTemp());
			model.addAttribute("tempMin", result.getLoTemp());
			model.addAttribute("tempMax", result.getHiTemp());
			model.addAttribute("wIcon", result.getIcon());
			model.addAttribute("country", "USA");
			model.addAttribute("state", result.getRegion());
			model.addAttribute("city", result.getCity());
			model.addAttribute("forecast", result.getForecastList());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in weather", e.getMessage());
		}

		// Getting Lunar Date
		// Current date
    Date today = new Date();

    // Convert to Chinese Lunar Calendar
    ChineseCalendar lunarCal = new ChineseCalendar();
    lunarCal.setTime(today);

    // Get lunar month and day
    int lunarMonth = lunarCal.get(Calendar.MONTH) + 1; // Months are 0-based
    int lunarDay = lunarCal.get(Calendar.DAY_OF_MONTH);
		String lunarDate =  "Month " + lunarMonth + ", Day " + lunarDay;
		model.addAttribute("lunarDate", lunarDate);		
		// System.out.println(lunar: lunarDate);

		// Spring uses InternalResourceViewResolver and return back index.jsp
		return VIEW_INDEX;
	}

	@RequestMapping(value = "/coolest", method = RequestMethod.GET)
	public String coolest() {
		return VIEW_COOLEST;
	}

	@RequestMapping(value = "/blank", method = RequestMethod.GET)
	public String blank() {
		return VIEW_BLANK;
	}
}