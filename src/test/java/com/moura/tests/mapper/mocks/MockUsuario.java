package com.moura.tests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import com.moura.dto.UsuarioDTO;
import com.moura.model.Usuario;

public class MockUsuario {

    public Usuario mockEntity() {
        return mockEntity(0);
    }

    public UsuarioDTO mockDTO(){
        return mockDTO(0);
    }

    public List<Usuario> mockEntityList() {
        List<Usuario> usuarios = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            usuarios.add(mockEntity(i));
        }
        return usuarios;
    }

    public List<UsuarioDTO> mockDTOList() {
        List<UsuarioDTO> usuarios = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            usuarios.add(mockDTO(i));
        }
        return usuarios;
    }

    public Usuario mockEntity(int number) {
        Usuario usuario = new Usuario();
        usuario.setId((long) number);
        usuario.setNome("Usuario Teste " + number);
        usuario.setPeso(70.0 + number);
        usuario.setAltura(1.70 + (number * 0.01));
        return usuario;
    }

    public UsuarioDTO mockDTO(int number) {
        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setId((long) number);
        usuario.setNome("Usuario Teste " + number);
        usuario.setIdade(33 + number);
        usuario.setPeso(70.0 + number);
        usuario.setAltura(1.70 + (number * 0.01));
        return usuario;
    }
}
