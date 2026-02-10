package com.moura.integrationtests.dto.wrappers.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.moura.integrationtests.dto.UsuarioDTO;

import java.io.Serializable;
import java.util.List;

public class UsuarioEmbeddedDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("lista de usuarios")
    private List<UsuarioDTO> listaDeUsuarios;

    public UsuarioEmbeddedDTO() {
    }

    public List<UsuarioDTO> getListaDeUsuarios() {
        return listaDeUsuarios;
    }

    public void setListaDeUsuarios(List<UsuarioDTO> listaDeUsuarios) {
        this.listaDeUsuarios = listaDeUsuarios;
    }
}
