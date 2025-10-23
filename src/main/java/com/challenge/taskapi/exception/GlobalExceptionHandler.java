package com.challenge.taskapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.challenge.taskapi.payload.response.MessageResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	/*
	 * @ExceptionHandler(TaskNotFoundException.class) public
	 * ResponseEntity<Map<String, Object>> handleTaskNotFound(TaskNotFoundException
	 * ex) { Map<String, Object> body = new HashMap<>(); body.put("timestamp",
	 * LocalDateTime.now()); body.put("message", ex.getMessage());
	 * body.put("status", HttpStatus.NOT_FOUND.value());
	 * 
	 * return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body); }
	 */

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handleBadCredentials(BadCredentialsException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Erro: Credenciais inválidas!"));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", "Erro: dado(s) inválidos!");
		body.put("details", ex.getMessage());
		body.put("status", HttpStatus.BAD_REQUEST.value());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}

}
