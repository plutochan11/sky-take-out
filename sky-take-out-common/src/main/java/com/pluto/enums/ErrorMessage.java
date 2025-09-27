package com.pluto.enums;

public enum ErrorMessage {
	INCORRECT_PASSWORD("Invalid password, please try again."),
	USER_NOT_FOUND("User not found"),
	DUPLICATE_USERNAME("This username is already taken, please choose another one."),
	DISH_ON_SALE("Dishes on sale cannot be deleted."),
	DISH_IN_A_MEAL("Dishes that are part of a meal cannot be deleted.");

	private final String message;

	ErrorMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
