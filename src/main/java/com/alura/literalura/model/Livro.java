package com.alura.literalura.model;

import java.util.HashSet;
import java.util.Set;

public class Livro {
    private String titulo;
    private Integer downloadCount;
    private Set<String> idiomas = new HashSet<>();
    private Set<Autor> autores = new HashSet<>();

    public Livro() {}

    public Livro(String titulo, Integer downloadCount,
                 Set<String> idiomas, Set<Autor> autores) {
        this.titulo = titulo;
        this.downloadCount = downloadCount;
        this.idiomas = idiomas;
        this.autores = autores;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Set<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Set<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Set<Autor> getAutores() {
        return autores;
    }

    public void setAutores(Set<Autor> autores) {
        this.autores = autores;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", downloadCount=" + downloadCount +
                ", idiomas=" + idiomas +
                '}';
    }
}
