package com.moura.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.moura.dto.UsuarioDTO;
import com.moura.services.UsuarioServices;

@RestController
@RequestMapping("/usuario")
public class UsuarioControllers {

	/*
	 * OBS: com a anotation "@Autowired" o spring vai injetar a instancia da classe
	 * "UsuarioService" sem a necessidade de usar o "new UsuarioServices();" desta
	 * forma: "private UsuarioServices usuarioService = new UsuarioServices();"
	 */
	@Autowired
	UsuarioServices usuarioService;

	// http://localhost:8080/usuario
	@GetMapping()
	public List<UsuarioDTO> findAll() {
		return usuarioService.findAll();
	}

	// http://localhost:8080/1
	@GetMapping("/{id}")
	public UsuarioDTO findById(@PathVariable("id") Long id) {
		return usuarioService.findById(id);
	}

	// CREATE
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) // retorna o "201 CREATED", padrão REST.
	public UsuarioDTO create(@RequestBody UsuarioDTO usuario) {
		return usuarioService.create(usuario);
	}

	// UPDATE
	@PutMapping("/{id}")
	public UsuarioDTO update(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
		usuarioDTO.setId(id);
		return usuarioService.update(usuarioDTO);
	}

	// DELETE retorna 204 No Content (Forma correta para o DELETE)
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		usuarioService.delete(id);
		return ResponseEntity.noContent().build();
	}

//DELETE retorna 200 (não é o ideal)
//@DeleteMapping("/{id}")
//public void delete(@PathVariable Long id) {
//	usuarioService.delete(id);
//}

	/*
	 * Diferença entre as principais anotações Anotação: Para que serve:
	 * 
	 * @RequestBody Dados do body (JSON)
	 * 
	 * @PathVariable Dados da URL (/usuarios/10)
	 * 
	 * @RequestParam Dados da query string (?page=1
	 */

}
