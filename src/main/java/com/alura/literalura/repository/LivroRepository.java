package com.alura.literalura.repository;

import com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    boolean existsByTitulo(String titulo);

    @Query("SELECT l FROM Livro l JOIN l.idiomas i WHERE i = :idioma")
    List<Livro> buscarLivrosPorIdioma(@Param("idioma") String idioma);
}
