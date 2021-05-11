package com.sbnz.CityExplorer.model;

public enum Price {
	FREE, CHEAP, MODERATE, EXPENSIVE;

	public static Price StringToEnum(String string) {

		String s = string.toUpperCase();
		if (s.equals("FREE")) {
			return Price.FREE;
		} else if (s.equals("CHEAP")) {
			return Price.CHEAP;
		} else if (s.equals("MODERATE")) {
			return Price.MODERATE;
		} else if (s.equals("EXPENSIVE")) {
			return Price.EXPENSIVE;
		} else {
			return null;
		}
	}
}