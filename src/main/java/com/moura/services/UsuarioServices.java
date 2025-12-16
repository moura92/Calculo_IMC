package com.moura.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moura.exception.ParametroInvalidoException;
import com.moura.model.Usuario;
import com.moura.repository.UsuarioRepository;

@Service
public class UsuarioServices {

	@Autowired
	UsuarioRepository repository;

	private Logger logger = Logger.getLogger(UsuarioServices.class.getName());
	/*
	 * Um logger é uma ferramenta usada em aplicações Java (e praticamente toda
	 * linguagem) para registrar mensagens, como: - erros - avisos - informações
	 * importantes - passos de execução - detalhes de requisições - eventos
	 * inesperados Logger é o jeito profissional de mostrar mensagens do sistema,
	 * substituindo System.out.println, com níveis, filtros e arquivos de log.
	 */

	public List<Usuario> findAll() {
		logger.info("Varios usuarios!");
		return repository.findAll();
	}

	public Usuario findById(Long id) {
		logger.info("Encontrando um usuario!");
		return repository.findById(id)
				.orElseThrow(() -> new ParametroInvalidoException("Nenhum registro encotrando para este ID"));
	}

	public Usuario create(Usuario usuario) {
		logger.info("Criando um usuario!");
		double imc = calculoImc(usuario);
		usuario.setImc(imc);
		usuario.setClassificaçao(classificarImc(imc));
		return repository.save(usuario);
	}

	public Usuario update(Usuario usuario) {
		logger.info("Atualizando usuario!");

		Usuario entidade = repository.findById(usuario.getId())
				.orElseThrow(() -> new ParametroInvalidoException("Nenhum registro encontrado para este ID"));

		entidade.setNome(usuario.getNome());
		entidade.setIdade(usuario.getIdade());
		entidade.setAltura(usuario.getAltura());
		entidade.setPeso(usuario.getPeso());

		double imc = calculoImc(entidade);
		entidade.setImc(imc);
		entidade.setClassificaçao(classificarImc(imc));

		return repository.save(entidade);
	}

	public void delete(Long id) {
		logger.info("Usuario deletado!");

		Usuario entidade = repository.findById(id)
				.orElseThrow(() -> new ParametroInvalidoException("Nenhum registro encontrado para este ID"));

		repository.delete(entidade);

	}

	private double calculoImc(Usuario dados) {
		if(dados.getPeso() <= 0.0 || dados.getAltura() <= 0.0) {
			throw new ParametroInvalidoException("Peso e altura devem ser informados!");
		}
		Double calculo = dados.getPeso() / Math.pow(dados.getAltura(), 2);
		Double imc = Math.round(calculo * 100.0) / 100.0;
		return imc;
	}

	private String classificarImc(double imc) {

		if (imc < 18.5) {
			return "Magreza";
		} else if (imc < 25) {
			return "Peso normal";
		} else if (imc < 30) {
			return "Sobrepeso";
		} else if (imc < 35) {
			return "Obesidade grau I";
		} else if (imc < 40) {
			return "Obesidade grau II";
		} else {
			return "Obesidade grau III";
		}
	}
}
