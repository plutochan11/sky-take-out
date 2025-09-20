package com.pluto.handler;

import com.pluto.exception.CustomException;
import com.pluto.exception.IncorrectPasswordException;
import com.pluto.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			  .body(e.getMessage());
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<String> handleCustomException(CustomException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			  .body(e.getMessage());
	}

	@ExceptionHandler(IncorrectPasswordException.class)
	public ResponseEntity<String> handleIncorrectPasswordException(IncorrectPasswordException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			  .body(e.getMessage());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			  .body(ex.getMessage());
	}

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<String> handleSQLIntegrityConstraintViolationException(
		  SQLIntegrityConstraintViolationException ex) {
		String message = ex.getMessage();
		// Divide the message into chunks based on spaces to see the error message
		String[] chunks = message.split("'");
		if (chunks.length > 2) {
			// Check if it's the dish nama conflict
			if (chunks[chunks.length - 1].contains("dish")) {
				String dishName = chunks[1];
				return ResponseEntity.status(HttpStatus.CONFLICT)
					  .body("Dish \"" + dishName + "\" already exists.");
			}
		}
		return ResponseEntity.status(HttpStatus.CONFLICT)
			  .body("Database constraint violation: " + message);
	}
}
