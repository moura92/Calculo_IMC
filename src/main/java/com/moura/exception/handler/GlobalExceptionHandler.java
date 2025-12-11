package com.moura.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.moura.exception.ExceptionResponse;
import com.moura.exception.ParametroInvalidoException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class) // trata os erros genericos.
	public final ResponseEntity<ExceptionResponse> handlerAllExceptions(Exception ex, WebRequest request) {
		ExceptionResponse response = new ExceptionResponse(
				LocalDateTime.now(), 
				ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(ParametroInvalidoException.class) //ERRO 400 -> o usuário enviou algo errado na requisição. exemplo: altura não enviada.
	public final ResponseEntity<ExceptionResponse> handlerBadRequestExceptions(ParametroInvalidoException ex,
			WebRequest request) {
		ExceptionResponse response = new ExceptionResponse(
				LocalDateTime.now(), 
				ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}
}
