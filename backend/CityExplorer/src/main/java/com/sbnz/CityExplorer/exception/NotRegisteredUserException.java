package com.sbnz.CityExplorer.exception;

public class NotRegisteredUserException extends Exception {
	public NotRegisteredUserException(String errorMessage) {
		super(errorMessage);
	}
}
