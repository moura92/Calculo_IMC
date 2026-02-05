package com.moura.services;

import com.moura.controllers.UsuarioControllers;
import com.moura.dto.UsuarioDTO;
import com.moura.exception.ObjetoNuloException;
import com.moura.exception.ParametroInvalidoException;
import com.moura.mapper.UsuarioMapper;
import com.moura.model.Usuario;
import com.moura.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UsuarioServices {

	@Autowired
	UsuarioRepository repository;
	@Autowired
	UsuarioMapper usuarioMapper;

	private static final Logger logger = LoggerFactory.getLogger(UsuarioServices.class);
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
		logger.info("Usuario encontrado pelo ID: {} ", id);
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

	@Transactional
	public UsuarioDTO disableUsuario(Long id) {

		repository.desativarUsuarios(id);

		var usuario = repository.findById(id)
				.orElseThrow(() -> new ParametroInvalidoException("Nenhum registro encontrado para este ID"));

		//usuario.setEnabled(false);

		logger.info("Usuario Desativado com sucesso. ID: {} | enabled: {}",
				usuario.getId(), usuario.getEnabled());


		var convertDTO = usuarioMapper.toDTO(usuario);
		addHateoasLinks(convertDTO);

		return convertDTO;
	}

    private void addHateoasLinks(UsuarioDTO dto) {
        dto.add(linkTo(methodOn(UsuarioControllers.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(UsuarioControllers.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(UsuarioControllers.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(UsuarioControllers.class).update(dto.getId(), dto)).withRel("update").withType("PUT"));
		dto.add(linkTo(methodOn(UsuarioControllers.class).disableUsuario(dto.getId())).withRel("disable").withType("PATCH"));
        dto.add(linkTo(methodOn(UsuarioControllers.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }
}
