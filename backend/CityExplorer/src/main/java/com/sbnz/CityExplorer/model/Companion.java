package com.sbnz.CityExplorer.model;

public enum Companion {
	ALONE, PARTNER, FAMILY, COLLEAGUES, FRIENDS;
	
	public static Companion StringToEnum(String string) {

		String s = string.toUpperCase();
		if (s.equals("ALONE")) {
			return Companion.ALONE;
		} else if (s.equals("PARTNER")) {
			return Companion.PARTNER;
		} else if (s.equals("FAMILY")) {
			return Companion.FAMILY;
		} else if (s.equals("COLLEAGUES")) {
			return Companion.COLLEAGUES;
		} else if (s.equals("FRIENDS")) {
			return Companion.FRIENDS;
		} else {
			return null;
		}
	}

}