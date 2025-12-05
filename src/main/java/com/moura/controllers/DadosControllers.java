package com.moura.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moura.exception.ParametroInvalidoException;
import com.moura.model.Dados;

@RestController
public class DadosControllers {

	private final AtomicLong id = new AtomicLong(); // Cria o ID

	// localhost:8080/dados
	@RequestMapping("/dados")
	public Dados dados(
			@RequestParam(required = false) Double peso, 
			@RequestParam(required = false) Double altura) {

		if (peso == null || peso <= 0) {
			throw new ParametroInvalidoException("ERRO! O peso não deve estar vazia.");
		}

		if (altura == null || altura <= 0) {
			throw new ParametroInvalidoException("ERRO! A altura não deve estar vazia.");
		}
		double calculo = peso / (altura * altura); // calculo IMC
		double imc = Math.round(calculo * 100.0) / 100.0; // arredonda para duas casas decimais

		return new Dados(id.incrementAndGet(), altura, peso, imc);
	}
}
