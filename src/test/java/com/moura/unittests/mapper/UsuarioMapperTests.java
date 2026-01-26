package com.moura.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.moura.dto.UsuarioDTO;
import com.moura.mapper.UsuarioMapper;
import com.moura.model.Usuario;
import com.moura.unittests.mapper.mocks.MockUsuario;

public class UsuarioMapperTests {

    private UsuarioMapper mapper;
    private MockUsuario inputObject;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(UsuarioMapper.class);
        inputObject = new MockUsuario();
    }

    @Test
    void parseEntityToDTOTest() {
        UsuarioDTO output = mapper.toDTO(inputObject.mockEntity());

        assertEquals(0L, output.getId());
        assertEquals("Usuario Teste 0", output.getNome());
        assertEquals(70.0, output.getPeso());
        assertEquals(1.70, output.getAltura());
    }

    @Test
    void parseEntityListToDTOListTest() {
        List<UsuarioDTO> outputList =
                mapper.toDTOList(inputObject.mockEntityList());

        UsuarioDTO outputZero = outputList.get(0);

        assertEquals(0L, outputZero.getId());
        assertEquals("Usuario Teste 0", outputZero.getNome());

        UsuarioDTO outputSeven = outputList.get(7);
        assertEquals(7L, outputSeven.getId());
        assertEquals("Usuario Teste 7", outputSeven.getNome());
    }

    @Test
    void parseDTOToEntityTest() {
        Usuario output = mapper.toEntity(inputObject.mockDTO());

        assertEquals(0L, output.getId());
        assertEquals("Usuario Teste 0", output.getNome());
        assertEquals(70.0, output.getPeso());
        assertEquals(1.70, output.getAltura());
    }

    @Test
    void parseDTOListToEntityListTest() {
        List<Usuario> outputList =
                mapper.toEntityList(inputObject.mockDTOList());

        Usuario outputTwelve = outputList.get(12);

        assertEquals(12L, outputTwelve.getId());
        assertEquals("Usuario Teste 12", outputTwelve.getNome());
    }
}
