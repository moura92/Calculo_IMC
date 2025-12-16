package com.moura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moura.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
