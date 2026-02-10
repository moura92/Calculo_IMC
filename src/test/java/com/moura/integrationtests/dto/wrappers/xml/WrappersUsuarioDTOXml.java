package com.moura.integrationtests.dto.wrappers.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.moura.integrationtests.dto.UsuarioDTO;

import java.io.Serializable;
import java.util.List;

public class WrappersUsuarioDTOXml implements Serializable {
    private static final long serialVersionUID = 1L;

    @JacksonXmlElementWrapper(localName = "content")
    @JacksonXmlProperty(localName = "contet")
    private List<UsuarioDTO> content;

    public WrappersUsuarioDTOXml() {
    }

    public List<UsuarioDTO> getContent() {
        return content;
    }

    public void setContent(List<UsuarioDTO> content) {
        this.content = content;
    }
}
