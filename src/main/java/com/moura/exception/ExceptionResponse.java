package com.moura.exception;

import java.time.LocalDateTime;

public record ExceptionResponse(LocalDateTime timestamp, String message, String details) {
	/*
	  Serve para formatar aestrutura da exceção retornando Json: 
	  EXEMPLO:
	  
	  { 
	  "timestamp": "2025-12-05T09:40:15.7591096", 
	  "message":"ERRO! A altura não deve estar vazia.", 
	  "details": "uri=/dados" }
	  
	  _____________________________________________________
	  
	  A classe ExceptionResponse:

		CAMPO			|	SERVE PARA
		timestamp 		|	Quando o erro ocorreu
		message			|	Por que o erro ocorreu (mensagem explicativa)
		details 		|	Onde ocorreu (URI, parâmetros, etc.)
	 */
}
