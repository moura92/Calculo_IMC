package com.moura.mapper;

import org.mapstruct.Mapper;

import com.moura.dto.UsuarioDTO;
import com.moura.model.Usuario;


@Mapper(componentModel = "spring")
public interface UsuarioMapper {

	//DTO -> Usuario
	Usuario toEntity(UsuarioDTO dto);
	
	//Usuario -> DTO
	UsuarioDTO toDTO(Usuario usuario);
}

/*
 O que está acontecendo aqui:
-	@Mapper 					→ Essa interface serve para converter objetos.
-	componentModel = "spring" 	→ Spring pode injetar com "@autoWired"
-	Interface 					→ MapStruct gera a classe
*/
