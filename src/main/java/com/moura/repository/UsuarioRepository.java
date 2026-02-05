package com.moura.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.moura.model.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Usuario u SET u.enabled = false WHERE u.id = :id")
    void desativarUsuarios(@Param("id") Long id);

}