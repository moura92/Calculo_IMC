package com.moura.file.importer.contact;

import com.moura.dto.UsuarioDTO;
import org.apache.xmlbeans.impl.xb.xmlconfig.Extensionconfig;

import java.io.InputStream;
import java.util.List;

public interface FileImporter {

    List<UsuarioDTO> importFile(InputStream inputStream) throws Exception;
}
