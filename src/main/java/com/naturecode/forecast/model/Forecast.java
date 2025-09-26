package com.naturecode.forecast.model;

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
public class Forecast {
	private String dayOfWeek;
	private int hiTemp;
	private int loTemp;
	private String icon;
}