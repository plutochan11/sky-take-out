package com.pluto;

import com.pluto.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class handler {
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<String> handleCustomException(CustomException e) {
		return ResponseEntity.status(404).body(e.getMessage());
	}
}
