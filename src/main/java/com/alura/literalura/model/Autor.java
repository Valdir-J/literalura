package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;

    @ManyToMany(mappedBy = "autores", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Livro> livros = new HashSet<>();

    public Autor() {
    }

    public Autor(String nome, Integer anoNascimento,
                 Integer anoFalecimento, Set<Livro> livros) {
        this.nome = nome;
        this.anoNascimento = anoNascimento;
        this.anoFalecimento = anoFalecimento;
        this.livros = livros;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(Integer anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public Set<Livro> getLivros() {
        return livros;
    }

    public void setLivros(Set<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "nome='" + nome + '\'' +
                ", anoNascimento=" + anoNascimento +
                ", anoFalecimento=" + anoFalecimento +
                ", livros=" + livros +
                '}';
    }
}
