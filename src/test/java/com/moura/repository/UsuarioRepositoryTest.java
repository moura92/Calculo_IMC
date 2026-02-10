package com.moura.repository;

import com.moura.integrationtests.testcontainers.AbstractIntegrationTest;
import com.moura.model.Usuario;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
/*
Por padrão, o @DataJpaTest troca o banco real por um H2 em memória,
Com essa anotação (@AutoConfigureTestDatabase) NÃO troca o banco, Usa o banco configurado no application.properties.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Os testes vão rodar na ordem definida por @Order
class UsuarioRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    private static Usuario usuario;

    @BeforeAll
    static void setUp() {
        usuario = new Usuario();
    }

    @Test
    @Order(1)
    void desativarUsuario() {
        Pageable pageable = PageRequest.of(
                0,
                12,
                Sort.Direction.ASC,
                "nome");

        usuario = usuarioRepository.nomeContainingIgnoreCase("Alisson", pageable).getContent().get(0);

        assertNotNull(usuario);
        assertNotNull(usuario.getId());
        assertEquals("Alisson", usuario.getNome());
        assertEquals(33, usuario.getIdade());
        assertEquals(78, usuario.getPeso());
        assertEquals(1.72, usuario.getAltura());
        assertEquals(BigDecimal.valueOf(26.37), usuario.getImc());
        assertTrue(usuario.getEnabled());
    }

    @Test
    @Order(2)
    void nomeContainingIgnoreCase() {

        Long id = usuario.getId();
        usuarioRepository.desativarUsuario(id);

        var resultado = usuarioRepository.findById(id);

        usuario = resultado.get();

        assertNotNull(usuario);
        assertNotNull(usuario.getId());
        assertEquals("Alisson", usuario.getNome());
        assertEquals(33, usuario.getIdade());
        assertEquals(78, usuario.getPeso());
        assertEquals(1.72, usuario.getAltura());
        assertEquals(BigDecimal.valueOf(26.37), usuario.getImc());
        assertFalse(usuario.getEnabled());
    }
}