package com.moura.integrationtests.dto.wrappers.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WrapperUsuarioDTOJson implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("_embedded")
    private UsuarioEmbeddedDTO embeddedDTO;

    public WrapperUsuarioDTOJson() {
    }

    public UsuarioEmbeddedDTO getEmbeddedDTO() {
        return embeddedDTO;
    }

    public void setEmbeddedDTO(UsuarioEmbeddedDTO embeddedDTO) {
        this.embeddedDTO = embeddedDTO;
    }
}
