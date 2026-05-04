package com.moura.file.exporter.contract;

import com.moura.dto.UsuarioDTO;
import org.springframework.core.io.Resource;

import java.util.List;

public interface FileExporter {

     Resource exportFile(List<UsuarioDTO> usuarios) throws Exception;
}
