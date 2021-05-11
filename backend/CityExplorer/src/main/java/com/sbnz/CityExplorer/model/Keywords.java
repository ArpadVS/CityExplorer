package com.sbnz.CityExplorer.model;

public enum Keywords {
	ROMANTIC, CHILL, ADVENTURE, TEAMBUILDING, LUXURY, FAMILY_FRIENDLY, EDUCATIONAL, NATURE, SPORT, HISTORY;

	public static Keywords StringToEnum(String string) {
		String s = string.toUpperCase();
		if (s.equals("ROMANTIC")) {
			return Keywords.ROMANTIC;
		} else if (s.equals("CHILL")) {
			return Keywords.CHILL;
		} else if (s.equals("ADVENTURE")) {
			return Keywords.ADVENTURE;
		} else if (s.equals("TEAMBUILDING")) {
			return Keywords.TEAMBUILDING;
		} else if (s.equals("LUXURY")) {
			return Keywords.LUXURY;
		} else if (s.equals("FAMILY_FRIENDLY")) {
			return Keywords.FAMILY_FRIENDLY;
		} else if (s.equals("EDUCATIONAL")) {
			return Keywords.EDUCATIONAL;
		} else if (s.equals("NATURE")) {
			return Keywords.NATURE;
		} else if (s.equals("SPORT")) {
			return Keywords.SPORT;
		} else if (s.equals("HISTORY")) {
			return Keywords.HISTORY;
		} else {
			return null;
		}
	}

}