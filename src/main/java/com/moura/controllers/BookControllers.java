package com.moura.controllers;

import com.moura.dto.BookDTO;
import com.moura.unittests.services.BookServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/book/v1")
@Tag(name = "Livro")
public class BookControllers {

    @Autowired
    BookServices bookServices;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Lista de Livros", description = "Lista todos os livros cadastrados")
    public ResponseEntity<List<BookDTO>> findAll(){
        return ResponseEntity.ok(bookServices.findAll());
    }

    @GetMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Encontre um livro pelo ID", description = "Mostra o usuario pelo ID correspondente")
    public BookDTO findById(@PathVariable Long id){
        return bookServices.findById(id);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE}
            ,produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Adicione um novo livro")
    public ResponseEntity<BookDTO> create(@Valid @RequestBody BookDTO bookDTO){
        BookDTO create = bookServices.create(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(create);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
                , produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Atualiza", description = "Atualize dados do livro pelo seu ID correspondente")
    public BookDTO update(@PathVariable Long id, @RequestBody BookDTO bookDTO){
        bookDTO.setId(id);
        return bookServices.update(bookDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar",description = "ATENÇÃO!! esta ação apaga para sempre o livro cadastrado")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        bookServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
