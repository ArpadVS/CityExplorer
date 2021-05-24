package com.sbnz.CityExplorer.dto;

public class SearchDTO {
	private String name;

	public SearchDTO() {
		super();
	}

	public SearchDTO(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
