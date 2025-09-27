package com.pluto.exception;

public class DeletionForbiddenException extends RuntimeException {
	public DeletionForbiddenException(String message) {
		super(message);
	}
}
