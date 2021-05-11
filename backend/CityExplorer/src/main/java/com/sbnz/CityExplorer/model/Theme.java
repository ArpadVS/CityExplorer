package com.sbnz.CityExplorer.model;

public enum Theme {
	NATURE, ADRENALINE, HISTORY, SPORT;

	public static Theme StringToEnum(String string) {

		String s = string.toUpperCase();
		if (s.equals("NATURE")) {
			return Theme.NATURE;
		} else if (s.equals("ADRENALINE")) {
			return Theme.ADRENALINE;
		} else if (s.equals("HISTORY")) {
			return Theme.HISTORY;
		} else if (s.equals("SPORT")) {
			return Theme.SPORT;
		} else {
			return null;
		}
	}
}