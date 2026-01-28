package com.moura.unittests.services;

import com.moura.dto.UsuarioDTO;
import com.moura.exception.ObjetoNuloException;
import com.moura.mapper.UsuarioMapper;
import com.moura.model.Usuario;
import com.moura.repository.UsuarioRepository;
import com.moura.services.UsuarioServices;
import com.moura.unittests.mapper.mocks.MockUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class UsuarioServicesTest {

    MockUsuario input;

    @InjectMocks
    private UsuarioServices services;

    @Mock
    UsuarioRepository repository;

    @Mock
    UsuarioMapper usuarioMapper;

    @BeforeEach
    void setUp() {
        input = new MockUsuario();
        // MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {

        Usuario usuario = input.mockEntity(1);
        usuario.setId(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(usuarioMapper.toDTO(usuario))
                .thenReturn(input.mockDTO(1));

        var resultado = services.findById(1L);
        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertNotNull(resultado.getLinks());

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/usuario/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/usuario/v1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/usuariov1/")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/usuario/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/usuario/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Usuario Teste 1", resultado.getNome());
        assertEquals(71.0, resultado.getPeso());
        assertEquals(1.71, resultado.getAltura());
    }

    @Test
    void create() {
        Usuario usuario = input.mockEntity(1);
        Usuario persisted = input.mockEntity(1);
        persisted.setId(1L);

        UsuarioDTO dto = input.mockDTO(1);

        when(usuarioMapper.toEntity(dto)).thenReturn(usuario);

        when(repository.save(usuario)).thenReturn(persisted);

        when(usuarioMapper.toDTO(persisted)).thenReturn(input.mockDTO(1));

        var resultado = services.create(dto);

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertNotNull(resultado.getLinks());

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/usuario/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/usuario/v1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/usuario/v1")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/usuario/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/usuario/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Usuario Teste 1", resultado.getNome());
        assertEquals(71.0, resultado.getPeso());
        assertEquals(1.71, resultado.getAltura());

    }

    @Test
    void testCreateUsuarioNulo() {
        Exception exception = assertThrows(ObjetoNuloException.class,
                () -> {
            services.create(null);
        });
        String message = "Não é permitido persistir um objeto nulo.";
        String atual = exception.getMessage();

        assertTrue(atual.contains(message));
    }


    @Test
    void update() {
        Usuario entity = input.mockEntity(1);
        entity.setId(1L);

        UsuarioDTO dto = input.mockDTO(1);
        dto.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        when(repository.save(entity)).thenReturn(entity);

        when(usuarioMapper.toDTO(entity)).thenReturn(input.mockDTO(1));

        var resultado = services.update(dto);

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertNotNull(resultado.getLinks());

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/usuario/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/usuario/v1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/usuario/v1")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/usuario/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/usuario/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Usuario Teste 1", resultado.getNome());
        assertEquals(71.0, resultado.getPeso());
        assertEquals(1.71, resultado.getAltura());
    }


    @Test
    void testUpdateUsuarioNulo() {
        Exception exception = assertThrows(ObjetoNuloException.class,
                () -> {
                    services.update(null);
                });
        String message = "Não é permitido persistir um objeto nulo.";
        String atual = exception.getMessage();

        assertTrue(atual.contains(message));
    }

    @Test
    void delete() {
        Usuario usuario = input.mockEntity(1);
        usuario.setId(1L);
        when(repository.findById(1L))
                .thenReturn(Optional.of(usuario));

        services.delete(1L);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Usuario.class));
    }

    @Test
    void findAll() {
        List<Usuario> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);

        when(usuarioMapper.toDTO(any(Usuario.class)))
                .thenAnswer(invocation -> {
                    Usuario usuario = invocation.getArgument(0);

                    UsuarioDTO dto = new UsuarioDTO();
                    dto.setId(usuario.getId());
                    dto.setNome(usuario.getNome());
                    dto.setPeso(usuario.getPeso());
                    dto.setAltura(usuario.getAltura());

                    return dto;
                });

        List<UsuarioDTO> usuarios = services.findAll();

        assertNotNull(usuarios);
        assertEquals(14,usuarios.size());

        var usuario1 = usuarios.get(1);
        assertNotNull(usuario1);
        assertNotNull(usuario1.getId());
        assertNotNull(usuario1.getLinks());

        assertNotNull(usuario1.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/usuario/v1/1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(usuario1.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/usuario/v1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(usuario1.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/usuario/v1")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(usuario1.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/usuario/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(usuario1.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/usuario/v1/1")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Usuario Teste 1", usuario1.getNome());
        assertEquals(71.0, usuario1.getPeso());
        assertEquals(1.71, usuario1.getAltura());


        var usuario4 = usuarios.get(4);
        assertNotNull(usuario4);
        assertNotNull(usuario4.getId());
        assertNotNull(usuario4.getLinks());

        assertNotNull(usuario4.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/usuario/v1/4")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(usuario4.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/usuario/v1")
                        && link.getType().equals("GET")
                )
        );

        assertNotNull(usuario4.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/usuario/v1")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(usuario4.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/usuario/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(usuario4.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/usuario/v1/4")
                        && link.getType().equals("DELETE")
                )
        );

        assertEquals("Usuario Teste 4", usuario4.getNome());
        assertEquals(74.0, usuario4.getPeso());
        assertEquals(1.74, usuario4.getAltura());
    }
}