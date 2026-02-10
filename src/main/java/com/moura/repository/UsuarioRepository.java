package com.moura.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.moura.model.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Usuario u SET u.enabled = false WHERE u.id = :id")
    void desativarUsuario(@Param("id") Long id);

/*
    @Query("""
                SELECT u
                FROM Usuario u
                WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%'))
            """)
*/
// OBS: A annotation @Query é uma forma manual de definir filtros (via JPQL ou SQL).
//Já o Spring Data JPA faz isso automaticamente, gerando a query com base no nome do metodo usando CamelCase.
    Page<Usuario> nomeContainingIgnoreCase(String nome, Pageable pageable);
    // O Spring Data JPA interpreta métodos derivados usando CamelCase (nome | Containing | IgnoreCase) e palavras-chave reservadas,
    // gerando automaticamente JPQL a partir do nome do metodo (nomeContainingIgnoreCase).

    /*
    JPQL:
      É uma linguagem de consulta:
        - Parecida com SQL
        - Mas que consulta ENTIDADES e ATRIBUTOS
        - Não tabelas e colunas
    */
}