package com.naturecode.forecast.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ibm.icu.util.ChineseCalendar;
import com.naturecode.forecast.credential.Credential;
import com.naturecode.forecast.model.Forecast;
import com.naturecode.forecast.model.AppSingleton;

@Controller
public class BaseController {
	private static final String VIEW_INDEX = "index";
	private static final String VIEW_COOLEST = "coolest";
	private static final String VIEW_BLANK = "blank";
	private static final String CONTROL = "control";
	private static final String BabyShark = "/home/pi/Work/BabyShark";

	@Value("${latitude}")
	private String latitude;

	@Value("${longitude}")
	private String longitude;

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BaseController.class);

	@RequestMapping(value = "/forecast", method = RequestMethod.GET)
	public String weather(ModelMap model, HttpServletRequest request) {
		int temp = 0;
		int icon = 0;
		int min = 0;
		int max = 0;
		String[] location = new String[] { "USA", "VA", "Herndon" };
		List<Forecast> forecast = new ArrayList<Forecast>();

		AppSingleton appSingleton = AppSingleton.getInstance();
		appSingleton.setupWeatherSymbols();

		try {
			// String latlng = Utils.getLatLngfromIp();
			String latlng = latitude + "," + longitude;
			location = Credential.getLocationfromLatLng(latlng);
			// logger.info("latlng: " + latlng);

			List<Integer> tempIcon = Credential.getCurrentTemp(latlng);
			temp = tempIcon.get(0);
			icon = tempIcon.get(1);

			List<Integer> minMax = Credential.getCurrentMinMax(latlng);
			min = minMax.get(0);
			max = minMax.get(1);

			forecast = Credential.getNext5Days(latlng);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in weather", e.getMessage());
		}

		model.addAttribute("forecast", forecast);

		model.addAttribute("tempString", temp);
		model.addAttribute("tempMin", min);
		model.addAttribute("tempMax", max);
		model.addAttribute("wIcon", appSingleton.getWeatherSymbols().get(icon).toUpperCase());
		model.addAttribute("country", location[0]);
		model.addAttribute("state", location[1]);
		model.addAttribute("city", location[2]);

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

	@RequestMapping(value = "/control", method = RequestMethod.GET)
	public String getControl(ModelMap model) {
		List<String> list = new ArrayList<>();
		try (Stream<String> stream = Files.lines(Paths.get(BabyShark + "/controls.txt"))) {
			list = stream.collect(Collectors.toList());
			String mainStatus = list.get(0).split(":")[1];
			String cronStatus = list.get(1).split(":")[1];
			model.addAttribute("Main", mainStatus.toUpperCase());
			model.addAttribute("Cron", cronStatus.toUpperCase());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return CONTROL;
	}

	@RequestMapping(value = "/control", params = "main", method = RequestMethod.POST)
	public String toggleMain(ModelMap model) {
		System.out.println("main");
		List<String> list = new ArrayList<>();
		try (Stream<String> stream = Files.lines(Paths.get(BabyShark + "/controls.txt"))) {
			list = stream.collect(Collectors.toList());
			String mainStatus = list.get(0).split(":")[1].equalsIgnoreCase("enable") ? "disable" : "enable";
			String cronStatus = list.get(1).split(":")[1];
			model.addAttribute("Main", mainStatus.toUpperCase());
			model.addAttribute("Cron", cronStatus.toUpperCase());

			// Update controls file
			String text = "main:" + mainStatus + "\ncron:" + cronStatus;
			Files.write(Paths.get(BabyShark + "/controls.txt"), text.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return CONTROL;
	}

	@RequestMapping(value = "/control", params = "cron", method = RequestMethod.POST)
	public String toggleCron(ModelMap model) {
		System.out.println("cron");
		List<String> list = new ArrayList<>();
		try (Stream<String> stream = Files.lines(Paths.get(BabyShark + "/controls.txt"))) {
			list = stream.collect(Collectors.toList());
			String mainStatus = list.get(0).split(":")[1];
			String cronStatus = list.get(1).split(":")[1].equalsIgnoreCase("enable") ? "disable" : "enable";
			model.addAttribute("Main", mainStatus.toUpperCase());
			model.addAttribute("Cron", cronStatus.toUpperCase());

			// Update controls file
			String text = "main:" + mainStatus + "\ncron:" + cronStatus;
			Files.write(Paths.get(BabyShark + "/controls.txt"), text.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return CONTROL;
	}

	// @RequestMapping(value = "/picture", method = RequestMethod.GET)
	// public String picture(ModelMap model) {
	// model.addAttribute("picture", "https://s3.amazonaws.com/thombasin/pic.jpg");
	// return VIEW_PICTURE;
	// }
	// @RequestMapping(value = "/recognize", method = RequestMethod.GET)
	// public String recognize(ModelMap model) {
	// model.addAttribute("picture", "https://s3.amazonaws.com/thombasin/pic.jpg");
	// return VIEW_PICTURE;
	// }
}