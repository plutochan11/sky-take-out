package com.pluto.enums;

public enum Status {
	ON_SALE(1),
	NOT_ON_SALE(0);

	private final int value;

	Status(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
