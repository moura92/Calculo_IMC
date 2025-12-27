package com.moura.mapper;

import org.mapstruct.Mapper;

import com.moura.dto.UsuarioDTO;
import com.moura.model.Usuario;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UsuarioMapper {

	//DTO -> Usuario
	Usuario toEntity(UsuarioDTO dto);
	
	//Usuario -> DTO
	UsuarioDTO toDTO(Usuario usuario);

    // List<Entity> -> List<DTO>
    List<UsuarioDTO> toDTOList(List<Usuario> entities);

    // List<DTO> -> List<Entity>
    List<Usuario> toEntityList(List<UsuarioDTO> dtos);
}

/*
 O que está acontecendo aqui:
-	@Mapper 					→ Essa interface serve para converter objetos.
-	componentModel = "spring" 	→ Spring pode injetar com "@autoWired"
-	Interface 					→ MapStruct gera a classe
*/
