package com.naturecode.forecast.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ForecastFull {
  private List<Forecast> forecastList;
  private int hiTemp;
	private int loTemp;
  private int currentTemp;
	private String icon;
  private String city;
  private String region;
  private String country;
}
