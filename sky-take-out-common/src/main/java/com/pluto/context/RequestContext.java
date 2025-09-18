package com.pluto.context;

public final class RequestContext {
	private static final ThreadLocal<Integer> requestUserId = new ThreadLocal<>();

	public static void setRequestUserId(Integer id) {
		requestUserId.set(id);
	}

	public static Integer getRequestUserId() {
		return requestUserId.get();
	}

	public static void clear() {
		requestUserId.remove();
	}
}
