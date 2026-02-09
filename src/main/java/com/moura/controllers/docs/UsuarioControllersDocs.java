package com.moura.controllers.docs;

import com.moura.dto.UsuarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Documentar SWAGGER
@Tag(name = "Usuario")
public interface UsuarioControllersDocs {

    // http://localhost:8080/api/v1/usuario
    @Operation(summary = "Lista de Usuarios"
                ,description = "Mostra a Lista de Usuarios cadastrados")
    ResponseEntity<PagedModel<EntityModel<UsuarioDTO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction

    );

    @Operation(summary = "Lista de Usuarios por nome"
                ,description = "Mostra a Lista de Usuarios cadastrados pelo nome")
    ResponseEntity<PagedModel<EntityModel<UsuarioDTO>>> nomeContainingIgnoreCase(
            @PathVariable("nome") String nome,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction

    );

    // http://localhost:8080/api/v1/usuario/1
    @Operation(summary = "Encontre um Usuario pelo ID"
                ,description = "Digite o ID do usuario que deseja encotra-lo")
    UsuarioDTO findById(@PathVariable("id") Long id);

    // CREATE
    @Operation(summary = "Cadastrar um novo Usuario")
    UsuarioDTO create(@RequestBody UsuarioDTO usuario);

    // UPDATE
    @Operation(
            summary = "Atualiza"
            ,description = "Atualize os dados de um usuario pelo ID correspondente")
    UsuarioDTO update(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO);

    // DELETE retorna 204 No Content (Forma correta para o DELETE)
    @Operation(
            summary = "Deletar"
            ,description = "ATENÇÃO! esta ação apaga um usuario para sempre no banco de dados")
    ResponseEntity<Void> delete(@PathVariable Long id);

    //DISABLE
    @Operation(summary = "Usuario desabilitado"
            ,description = "Digite o ID do usuario que deseja desabilitar")
    UsuarioDTO disableUsuario(@PathVariable("id") Long id);
}
