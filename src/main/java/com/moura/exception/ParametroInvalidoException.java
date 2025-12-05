package com.moura.exception;

//@ResponseStatus(HttpStatus.BAD_REQUEST) // Status code: Retorna um codigoHTTP. EX: "erro 404"
public class ParametroInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ParametroInvalidoException(String message) {
		super(message);
	}
}
