package com.sbnz.CityExplorer.model;

public enum Space {
	SMALL, MEDIUM, LARGE;

	public static Space StringToEnum(String string) {

		String s = string.toUpperCase();
		if (s.equalsIgnoreCase("SMALL")) {
			return Space.SMALL;
		} else if (s.equalsIgnoreCase("MEDIUM")) {
			return Space.MEDIUM;
		} else if (s.equalsIgnoreCase("LARGE")) {
			return Space.LARGE;
		} else {
			return null;
		}
	}
}