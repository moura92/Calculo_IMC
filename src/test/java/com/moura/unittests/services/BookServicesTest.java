package com.moura.unittests.services;

import com.moura.dto.BookDTO;
import com.moura.exception.ParametroInvalidoException;
import com.moura.mapper.BookMapper;
import com.moura.model.Book;
import com.moura.repository.BookRepository;
import com.moura.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServicesTest {

    @Mock
    BookRepository bookRepository;

    @Mock
    BookMapper bookMapper;

    @InjectMocks
    BookServices bookServices;
    /*
    -> Cria o UsuarioService
    -> Injeta o repository falso dentro dele
    -> cria o service com new.
     */

    @Test
    void findById() {///deve Busca rBook Por Id Com Sucesso()
        Book book = new Book();
        book.setId(1L);
        book.setAuthor("TESTE de mock, BOOK.");

        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        //TRADUÇÃO: “Quando alguém chamar repository.findById(1), devolva esse usuário”

        when(bookMapper.toDTO(book)).thenReturn(bookDTO);

        var resultado = bookServices.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());

        verify(bookRepository).findById(1L);
        verify(bookMapper).toDTO(book);
    }

    @Test
    void exceptionNoId(){ /// Deve Lancar ResourceNotFoundException Quando Book Nao Existir

        /// Define um ID válido, mas inexistente
        Long bookId = 999L;

        /// Simula o comportamento do repository:
        /// findById retorna vazio (nenhum registro encontrado)
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        /// Executa o metodo findById do service e verifica se a exceção ResourceNotFoundException é lançada
        assertThrows(
                ParametroInvalidoException.class,
                ()-> bookServices.findById(bookId));

        /// Garante que o repository NÃO foi chamado
        verify(bookRepository).findById(any());

        // Garante que o mapper NÃO foi chamado,
        // pois não existe Book para converter em DTO
        verify(bookMapper, never()).toDTO(any(Book.class));
    }

    @Test
    void exceptionIdZero(){ /// exception Quando Id For Zero Ou Negativo

        assertThrows(ParametroInvalidoException.class, ()-> bookServices.findById(0L));
        assertThrows(ParametroInvalidoException.class, ()-> bookServices.findById(-1L));

    /// Garante que o repository NÃO foi chamado
        verify(bookRepository, never()).findById(any());
    }
    @Test
    void findAll() { /// Buscar Todos Os Books Com Sucesso

        /// Cria a classe responsável por gerar dados de teste
        MockBook mockBook = new MockBook();

        /// Cria uma lista de entidades Book simulando registros do banco
        List<Book> bookList = mockBook.mockEntityList();

        /// Cria a lista de DTOs correspondente à lista de entidades
        List<BookDTO> bookDTOList = mockBook.bookDTOList();

        /// Define o comportamento do repository:
        /// Quando o metodo findAll() for chamado, ele deve retornar a lista fake de books
        when(bookRepository.findAll()).thenReturn(bookList);

        /// Define o comportamento do mapper:
        /// Para cada Book da lista, o mapper deve converter para BookDTO
        for (int i=0; i<bookList.size(); i++){
            when(bookMapper.toDTO(bookList.get(i))).thenReturn(bookDTOList.get(i));
        }

        /// Executa o metodo que está sendo testado
        var resultado = bookServices.findAll();

        /// Verifica se o resultado não é nulo
        assertNotNull(resultado);

        /// Verifica se a quantidade retornada é a esperada
        assertEquals(bookList.size(),resultado.size());

        /// Verifica alguns dados para garantir que o mapeamento funcionou
        assertEquals("Author Test 0", resultado.get(0).getAuthor());
        assertEquals("Author Test 13", resultado.get(13).getAuthor());

        /// Verifica se o repository foi chamado exatamente uma vez
        verify(bookRepository).findAll();

        /// Verifica se o mapper foi chamado para cada entidade da lista
        verify(bookMapper, times(bookList.size())).toDTO(any(Book.class));
    }

    @Test
    void create() {
        /// Cria um objeto Book (entidade) simulando a entrada do usuário
        Book book = new Book();
        book.setTitle("Clean Code");
        book.setAuthor("Robert C. Martin");
        book.setPrice(new BigDecimal("99.99"));

        //// Cria o DTO que o service deve retornar
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Clean Code");
        bookDTO.setAuthor("Robert C. Martin");
        bookDTO.setPrice(new BigDecimal("99.99"));

        /// Simula o mapper DTO -> Entity
        when(bookMapper.toEntity(bookDTO)).thenReturn(book);
        /// Simula o repository.save()
        when(bookRepository.save(book)).thenReturn(book);
        /// Simula o comportamento do mapper Entity -> DTO
        when(bookMapper.toDTO(book)).thenReturn(bookDTO);

        /// Executa o metodo que estamos testando
        BookDTO resultado = bookServices.create(bookDTO);

        /// Verifica se o retorno não é nulo
        assertNotNull(resultado);

        /// Verifica se os dados retornados são os esperados
        assertEquals("Clean Code", resultado.getTitle());
        assertEquals("Robert C. Martin", resultado.getAuthor());
        assertEquals(new BigDecimal("99.99"),resultado.getPrice());

        /// Verifica se o ID foi forçado para null antes do save
        assertNull(book.getId());

        verify(bookMapper, times(1)).toEntity(bookDTO);
        /// Verifica se o metodo save foi chamado exatamente 1 vez
        verify(bookRepository, times(1)).save(book);
        /// Verifica se o mapper foi chamado
        verify(bookMapper, times(1)).toDTO(book);

        /// Garante que não houve outras interações inesperadas
        verifyNoMoreInteractions(bookMapper, bookRepository);
    }

    @Test
    void update() {
        LocalDateTime data = LocalDateTime.now();

        /// DTO de entrada
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setTitle("Clean Code");
        bookDTO.setAuthor("Robert C. Martin");
        bookDTO.setPrice(new BigDecimal("99.99"));
        bookDTO.setDate(data);

        /// Entidade existente
        Book entidade = new Book();
        entidade.setId(1L);

        /// MOCK do findById
        when(bookRepository.findById(1L)).thenReturn(Optional.of(entidade));
        /// MOCK do save (retorna a propria entidade ja alterada)
        when(bookRepository.save(entidade)).thenReturn(entidade);
        /// Mock do mapper Entity -> DTO
        when(bookMapper.toDTO(any(Book.class))).thenReturn(bookDTO);

        BookDTO resultado = bookServices.update(bookDTO);

        /// Verifica se o DTO retornado pelo service não é nulo
        /// Garante que o metodo update realmente retornou algo
        assertNotNull(resultado);
        /// Verifica se o título retornado no DTO é o esperado
        /// Confirma que os dados do DTO de saída estão corretos
        assertEquals("Clean Code", resultado.getTitle());
        /// Verifica se a data retornada no DTO é a mesma enviada
        /// Garante que o campo date foi corretamente propagado
        assertEquals(data, resultado.getDate());

        /// Verifica se os campos da ENTIDADE foram atualizados
        /// Isso garante que o service copiou corretamente os dados do DTO
        /// para a entidade antes de salvar no repositório
        assertEquals("Clean Code", entidade.getTitle());
        assertEquals("Robert C. Martin", entidade.getAuthor());
        assertEquals(new BigDecimal("99.99"), entidade.getPrice());
        assertEquals(data, entidade.getDate());

        /// Verifica se o repository buscou o livro pelo ID
        /// Confirma que o fluxo do update passou pelo findById
        verify(bookRepository).findById(1L);
        /// Verifica se o repository salvou a entidade atualizada
        /// Garante que o save foi chamado após a atualização dos campos
        verify(bookRepository).save(entidade);
        /// Verifica se o mapper converteu a entidade em DTO
        /// Confirma que o retorno do service veio do mapper
        verify(bookMapper).toDTO(entidade);
    }

    @Test
    void delete() {
        /// ID válido para exclusão
        Long id = 1L;

        /// Entidade existente no banco
        Book entidade = new Book();
        entidade.setId(id);

        /// Simula que o livro existe
        when(bookRepository.findById(id)).thenReturn(Optional.of(entidade));

        /// Executa o metodo que estamos testando
        bookServices.delete(id);

        /// Verifica se o findById foi chamado
        verify(bookRepository).findById(id);

        /// Verifica de o delete foi chamado com a entidade correta
        verify(bookRepository).delete(entidade);

        /// Garante que não houve outras interações
        verifyNoMoreInteractions(bookRepository);
    }
}