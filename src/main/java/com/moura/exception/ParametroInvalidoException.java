package com.moura.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // Status code: Retorna um codigoHTTP. EX: "erro 404"
public class ParametroInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ParametroInvalidoException(String message) {
		super(message);
	}
}
