package com.moura.unittests.services;

import com.moura.controllers.BookControllers;
import com.moura.dto.BookDTO;
import com.moura.exception.ObjetoNuloException;
import com.moura.exception.ParametroInvalidoException;
import com.moura.mapper.BookMapper;
import com.moura.model.Book;
import com.moura.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

@Slf4j
@Service
public class BookServices {

    private static final Logger log = LoggerFactory.getLogger(BookServices.class);
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookMapper bookMapper;

    public List<BookDTO> findAll(){
        log.info("Buscando lista de livros");

        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDTO)
                .peek(this::addHateoasLinks)
                .toList();
    }

    public BookDTO findById(Long id){

        if (id == null || id <= 0){
            throw new ParametroInvalidoException("ID inválido");
        }

        log.info("Buscando Livro pelo ID");

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ParametroInvalidoException("Nenhum livro encontrado no banco de dados"));
        var dto = bookMapper.toDTO(book);
        addHateoasLinks(dto);
        return dto;
    }

    public BookDTO create(BookDTO bookDTO){
        if (bookDTO == null) throw new ObjetoNuloException("LIvro não pode ser nulo");
        log.info("Livro adicionado com sucesso!");

        Book book = bookMapper.toEntity(bookDTO);   /// DTO -> ENTITY
        book.setId(null);   /// Garante INSERT
        var dto = bookMapper.toDTO(bookRepository.save(book));  /// save + Entity -> DTO
        addHateoasLinks(dto);   /// Adiciona lins
        return dto;
    }

    public BookDTO update(BookDTO bookDTO){
        if (bookDTO == null) throw new ObjetoNuloException();
        log.info("Atualizando livro");
        Book entidade = bookRepository.findById(bookDTO.getId())
                .orElseThrow(()-> new ParametroInvalidoException("Livro não encontrado"));
        entidade.setAuthor(bookDTO.getAuthor());
        entidade.setDate(bookDTO.getDate());
        entidade.setPrice(bookDTO.getPrice());
        entidade.setTitle(bookDTO.getTitle());
        var dto = bookMapper.toDTO(bookRepository.save(entidade));
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id){
        log.info("Livro delete do banco de dados");
        Book entidade = bookRepository.findById(id)
                .orElseThrow(()-> new ParametroInvalidoException("Nenhum registro encontrado no banco"));
        bookRepository.delete(entidade);
    }

    private void addHateoasLinks(BookDTO dto){
        dto.add(linkTo(methodOn(BookControllers.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(BookControllers.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(BookControllers.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(BookControllers.class).update(dto.getId(), dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(BookControllers.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }

}
