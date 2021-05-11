package com.sbnz.CityExplorer.model;

public enum Transportation {
	BY_FOOT, AUTO, BUS;

	public static Transportation StringToEnum(String string) {

		String s = string.toUpperCase();
		if (s.equals("BY_FOOT")) {
			return Transportation.BY_FOOT;
		} else if (s.equals("AUTO")) {
			return Transportation.AUTO;
		} else if (s.equals("BUS")) {
			return Transportation.BUS;
		} else {
			return null;
		}
	}
}