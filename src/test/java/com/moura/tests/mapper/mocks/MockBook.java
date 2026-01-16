package com.moura.tests.mapper.mocks;

import com.moura.dto.BookDTO;
import com.moura.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por criar objetos Book e BookDTO
 * para serem usados nos testes.
 *
 * Evita repetição de código e facilita manutenção dos testes.
 */
public class MockBook {
    /**
     * Cria um Book padrão (id = 0).
     * Útil quando o id não é relevante no teste.
     */
    public Book mockEntity(){
        return mockEntity(0);
    }

    /**
     * Cria um BookDTO padrão (id = 0).
     */
    public BookDTO mockDTO(){
        return mockDTO(0);
    }

    /**
     * Cria uma lista de Books simulando vários registros.
     * Muito usada em testes de findAll().
     */
    public List<Book> mockEntityList(){
        List<Book> books = new ArrayList<>();
        for (int i =0; i<14; i++){
            books.add(mockEntity(i));
        }
        return books;
    }

    /**
     * Cria uma lista de BookDTOs.
     * Normalmente usada para validar retorno de listas no service.
     */
    public List<BookDTO> bookDTOList(){
        List<BookDTO> books = new ArrayList<>();
        for (int i=0; i<14; i++){
            books.add(mockDTO(i));
        }
        return books;
    }
    /**
     * Cria um Book com dados previsíveis.
     * O parâmetro number é usado para gerar valores diferentes
     * e facilitar validações nos testes.
     */
    public Book mockEntity(int number){
        Book book = new Book();
        book.setId((long)number);
        book.setAuthor("Author Test " + number);
        book.setTitle("Book Title Test "+ number);
        return book;
    }

    /**
     * Cria um BookDTO com dados previsíveis.
     * Geralmente usado como retorno esperado nos testes.
     */
    public BookDTO mockDTO(int number){
        BookDTO book = new BookDTO();
        book.setId((long) number);
        book.setAuthor("Author Test " + number);
        book.setTitle("Book Title Test " + number);
        return book;
    }
}
