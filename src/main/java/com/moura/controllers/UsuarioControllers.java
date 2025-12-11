package com.moura.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moura.UsuarioServices;
import com.moura.exception.ParametroInvalidoException;
import com.moura.model.Usuario;

@RestController
public class UsuarioControllers {

	@Autowired
	/*
	 * 	OBS: com a anotation "@Autowired" o spring vai injetar a instancia da classe
	 * 		 "UsuarioService" sem a necessidade de usar o "new UsuarioServices();"
	 * 	desta forma: "private UsuarioServices usuarioService = new UsuarioServices();"
	 */
	private UsuarioServices usuarioService;
	
	private final AtomicLong id = new AtomicLong(); // Cria o ID

	//http://localhost:8080/id
	@RequestMapping("/{id}")
	public Usuario teste(@PathVariable("id") String id) {
		return usuarioService.findById(id);
	}

	// http://localhost:8080/dados
	@RequestMapping("/dados")
	public Usuario dados(
			@RequestParam(defaultValue = "Alisson de moura") String nome,
			@RequestParam(defaultValue = "29") int idade,
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

		String tabela = "";

		if (imc < 18.5) {tabela = "Magreza";
		}
		else if(imc <= 24.9) {tabela = "Peso Normal";
		}
		else if(imc <= 29.9) {tabela = "Sobrepeso";
		}
		else if(imc <= 34.9) {tabela = "Obesidade Grau I";
		}
		else if(imc <= 39.9) {tabela = "Obesidade Grau II";
		}
		else if(imc >= 40) {tabela = "Obesiade Grau III - (mórbida)";
		}
		
		
		return new Usuario(id.incrementAndGet(), nome, idade, altura, peso, imc, tabela);
	}
}