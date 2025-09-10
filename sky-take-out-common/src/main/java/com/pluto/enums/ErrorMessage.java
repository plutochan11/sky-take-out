package com.pluto.enums;

public enum ErrorMessage {
	INCORRECT_PASSWORD("Invalid password, please try again."),
	USER_NOT_FOUND("User not found, please check your username."),
	DUPLICATE_USERNAME("This username is already taken, please choose another one.");

	private final String message;

	ErrorMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
