package com.sbnz.CityExplorer.model;

public enum Location {
	CITY_CENTER, SUBURBS, OUTSIDE_THE_CITY;

	public static Location StringToEnum(String string) {

		String s = string.toUpperCase();
		if (s.equals("CITY_CENTER")) {
			return Location.CITY_CENTER;
		} else if (s.equals("SUBURBS")) {
			return Location.SUBURBS;
		} else if (s.equals("OUTSIDE_THE_CITY")) {
			return Location.OUTSIDE_THE_CITY;
		} else {
			return null;
		}
	}
}