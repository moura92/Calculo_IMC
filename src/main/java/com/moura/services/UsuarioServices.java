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
		logger.info("Lista de Usuarios");
		return repository.findAll();
	}

	public Usuario findById(Long id) {
		logger.info("Usuario encontrado pelo ID " + id);
		return repository.findById(id)
				.orElseThrow(() -> new ParametroInvalidoException("Nenhum registro encotrando para este ID"));
	}

	public Usuario create(Usuario usuario) {
		logger.info("Novo Usuario criado!");
		
		usuario.calculoImc();
		return repository.save(usuario);
	}

	public Usuario update(Usuario usuario) {
		logger.info("Usuario atualizado!");

		Usuario entidade = repository.findById(usuario.getId())
				.orElseThrow(() -> new ParametroInvalidoException("Nenhum registro encontrado para este ID"));

		entidade.setNome(usuario.getNome());
		entidade.setIdade(usuario.getIdade());
		entidade.setAltura(usuario.getAltura());
		entidade.setPeso(usuario.getPeso());
		entidade.calculoImc();

		return repository.save(entidade);
	}

	public void delete(Long id) {
		logger.info("Usuario deletado!");

		Usuario entidade = repository.findById(id)
				.orElseThrow(() -> new ParametroInvalidoException("Nenhum registro encontrado para este ID"));

		repository.delete(entidade);

	}

}
