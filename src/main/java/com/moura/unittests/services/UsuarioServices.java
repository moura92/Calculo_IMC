package com.moura.unittests.services;

import com.moura.controllers.UsuarioControllers;
import com.moura.dto.UsuarioDTO;
import com.moura.exception.ObjetoNuloException;
import com.moura.exception.ParametroInvalidoException;
import com.moura.mapper.UsuarioMapper;
import com.moura.model.Usuario;
import com.moura.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UsuarioServices {

	@Autowired
	UsuarioRepository repository;
	@Autowired
	UsuarioMapper usuarioMapper;

	private Logger logger = Logger.getLogger(UsuarioServices.class.getName());
	/*
	 * Um logger é uma ferramenta usada em aplicações Java (e praticamente toda
	 * linguagem) para registrar mensagens, como: - erros - avisos - informações
	 * importantes - passos de execução - detalhes de requisições - eventos
	 * inesperados Logger é o jeito profissional de mostrar mensagens do sistema,
	 * substituindo System.out.println, com níveis, filtros e arquivos de log.
	 */

	public List<UsuarioDTO> findAll() {
		logger.info("Buscando Lista de Usuarios");

		var usuarios =  repository.findAll()
                .stream()
                .map(usuarioMapper::toDTO)
                .toList();
        usuarios.forEach(this::addHateoasLinks);
        return usuarios;
	}

	public UsuarioDTO findById(Long id) {
		logger.info("Usuario encontrado pelo ID " + id);
		Usuario usuario = repository.findById(id)
				.orElseThrow(() -> new ParametroInvalidoException("Nenhum registro encotrando para este ID"));
		var dto = usuarioMapper.toDTO(usuario);
        addHateoasLinks(dto);
        return dto;
	}

    public UsuarioDTO create(UsuarioDTO usuarioDTO) {

        if (usuarioDTO == null) throw new ObjetoNuloException();

        logger.info("Novo Usuario criado!");

		Usuario usuario = usuarioMapper.toEntity(usuarioDTO);

		usuario.calculoImc();

		Usuario salvo = repository.save(usuario);
		var dto = usuarioMapper.toDTO(salvo);
        addHateoasLinks(dto);
        return dto;
	}

	public UsuarioDTO update(UsuarioDTO usuarioDTO) {

        if (usuarioDTO == null) throw new ObjetoNuloException();

        logger.info("Usuario atualizado!");

		Usuario entidade = repository.findById(usuarioDTO.getId())
				.orElseThrow(() -> new ParametroInvalidoException("Nenhum registro encontrado para este ID"));

		entidade.setNome(usuarioDTO.getNome());
		entidade.setIdade(usuarioDTO.getIdade());
		entidade.setAltura(usuarioDTO.getAltura());
		entidade.setPeso(usuarioDTO.getPeso());
		entidade.calculoImc();

		var dto = usuarioMapper.toDTO(repository.save(entidade));
        addHateoasLinks(dto);
        return dto;
	}

	public void delete(Long id) {
		logger.info("Usuario deletado!");

		Usuario entidade = repository.findById(id)
				.orElseThrow(() -> new ParametroInvalidoException("Nenhum registro encontrado para este ID"));

		repository.delete(entidade);

	}
    private void addHateoasLinks(UsuarioDTO dto) {
        dto.add(linkTo(methodOn(UsuarioControllers.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(UsuarioControllers.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(UsuarioControllers.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(UsuarioControllers.class).update(dto.getId(), dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(UsuarioControllers.class).delete(dto.getId())).withRel("detele").withType("DELETE"));
    }
}
