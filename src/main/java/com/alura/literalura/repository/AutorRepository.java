package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByNome(String nome);

    @Query("SELECT a FROM Autor a WHERE a.anoNascimento <= :ano AND (a.anoFalecimento IS NULL OR a.anoFalecimento >= :ano)")
    List<Autor> listarAutoresVivosNoAno(@Param("ano") int ano);

    @Query("SELECT a FROM Autor a WHERE a.nome ILIKE %:nomeAutor%")
    List<Autor> buscarAutorPorNome(@Param("nomeAutor") String nomeAutor);
}
