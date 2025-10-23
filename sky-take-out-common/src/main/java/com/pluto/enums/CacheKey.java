package com.pluto.enums;

import java.util.concurrent.TimeUnit;

public enum CacheKey {
	DISH("dish:"),
	DISH_CLASSIFICATION("dish_classification:"),
	DISHES_DISH_CLASSIFICATION("dish:dish_classification:"),
	MEAL("meal:");

	private final String prefix;
	private final long ttl = 10L;
	private final TimeUnit timeUnit = TimeUnit.MINUTES;

	CacheKey(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * Get the complete cache key by appending the given ID
	 * @param id The ID
	 * @return The complete cache key
	 */
	public String key(String id) {
		return prefix + id;
	}

	public long ttl() { return ttl; }
	public TimeUnit timeUnit() { return timeUnit; }
}
