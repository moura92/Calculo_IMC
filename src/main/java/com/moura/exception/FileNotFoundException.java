package com.moura.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // Status code: Retorna um codigoHTTP. EX: "erro 404"
public class FileNotFoundException extends RuntimeException {

	public FileNotFoundException(String message) {
		super(message);
	}


	public FileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
