package com.moura.controllers;

import com.moura.controllers.docs.UsuarioControllersDocs;
import com.moura.dto.UsuarioDTO;
import com.moura.services.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario/v1")
public class UsuarioControllers implements UsuarioControllersDocs {

	/*
	 * OBS: com a anotation "@Autowired" o spring vai injetar a instancia da classe
	 * "UsuarioService" sem a necessidade de usar o "new UsuarioServices();"
	 * desta forma: "private UsuarioServices usuarioService = new UsuarioServices();"
	 */
	@Autowired
	UsuarioServices usuarioService;

	// http://localhost:8080/api/usuario/v1
	@CrossOrigin(origins = "http://www.testcors.com.br")
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@Override
	public ResponseEntity<PagedModel<EntityModel<UsuarioDTO>>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "12") Integer size,
			@RequestParam(value = "direction", defaultValue = "asc") String direction
	) {
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC: Sort.Direction.ASC; //Define se a ordenação será DESC (decrescente) ou ASC (crescente), com base no valor da variável direction.
		Pageable pageable = PageRequest.of(page, size, sortDirection, "nome");
		//OBS: O "nome" define a ordenação da lista pelo nome do usuario.
		return ResponseEntity.ok(usuarioService.findAll(pageable));
	}

	@CrossOrigin(origins = "http://www.testcors.com.br")
	@GetMapping(
			value = "/nomeContainingIgnoreCase/{nome}",
			produces = {
							MediaType.APPLICATION_JSON_VALUE,
							MediaType.APPLICATION_XML_VALUE})
	@Override
	public ResponseEntity<PagedModel<EntityModel<UsuarioDTO>>> nomeContainingIgnoreCase(
			@PathVariable("nome") String nome,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "12") Integer size,
			@RequestParam(value = "direction", defaultValue = "asc") String direction
	) {
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC: Sort.Direction.ASC; //Define se a ordenação será DESC (decrescente) ou ASC (crescente), com base no valor da variável direction.
		Pageable pageable = PageRequest.of(page, size, sortDirection, "nome");
		//OBS: O "nome" define a ordenação da lista pelo nome do usuario.
		return ResponseEntity.ok(usuarioService.nomeContainingIgnoreCase(nome, pageable));
	}

	// http://localhost:8080/api/usuario/v1/1
	//@CrossOrigin( // Só permite acessar por esses enderenço:
	//		origins = {"http://localhost:8080",
	//				"http://www.moura.com.br"})
	@GetMapping(
			value = "/{id}",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@Override
	public UsuarioDTO findById(@PathVariable("id") Long id) {
        var usuario = usuarioService.findById(id);
       // usuario.setData(new Date());
        return usuario;
	}

	// CREATE
	@PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	@Override
	public UsuarioDTO create(@RequestBody UsuarioDTO usuario) {
		return usuarioService.create(usuario);
	}

	// UPDATE
	@PutMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@Override
	public UsuarioDTO update(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
		usuarioDTO.setId(id);
		return usuarioService.update(usuarioDTO);
	}

	// PATCH
	@PatchMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@Override
	public UsuarioDTO disableUsuario(@PathVariable("id") Long id){
		return usuarioService.disableUsuario(id);
	}

	// DELETE retorna 204 No Content (Forma correta para o DELETE)
	@DeleteMapping("/{id}")
	@Override
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
