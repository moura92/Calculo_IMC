package com.moura.exception.handler;

import java.time.LocalDateTime;

import com.moura.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
	public final ResponseEntity<ExceptionResponse> handlerNotfoundExceptions(ParametroInvalidoException ex,
			WebRequest request) {
		ExceptionResponse response = new ExceptionResponse(
				LocalDateTime.now(), 
				ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ObjetoNuloException.class) //ERRO 400 -> o usuário enviou algo errado na requisição. exemplo: altura não enviada.
	public final ResponseEntity<ExceptionResponse> handleRequestExceptions(ParametroInvalidoException ex,
			WebRequest request) {
		ExceptionResponse response = new ExceptionResponse(
				LocalDateTime.now(),
				ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(FileNotFoundException.class) //ERRO 400 -> o usuário enviou algo errado na requisição. exemplo: altura não enviada.
	public final ResponseEntity<ExceptionResponse> handleFileNotFoundException(ParametroInvalidoException ex,
			WebRequest request) {
		ExceptionResponse response = new ExceptionResponse(
				LocalDateTime.now(),
				ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(FileStorageException.class) //ERRO 400 -> o usuário enviou algo errado na requisição. exemplo: altura não enviada.
	public final ResponseEntity<ExceptionResponse> handleFileStorageException(ParametroInvalidoException ex,
			WebRequest request) {
		ExceptionResponse response = new ExceptionResponse(
				LocalDateTime.now(),
				ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
