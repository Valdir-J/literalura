package com.alura.literalura.principal;

import com.alura.literalura.dto.AutorDTO;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.service.GutendexAPI;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Principal {
    private final Scanner scanner = new Scanner(System.in);
    private final GutendexAPI gutendexApi = new GutendexAPI();
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void exibeMenu() {
        int opcao = -1;
        String menuMensagem = """
                \n------------------------------
                Escolha o número da opção:
                1 - Buscar livro pelo título
                2 - listar livros registrados
                3 - listar autores registrados
                
                0 - Sair
                ------------------------------
                """;

        while (opcao != 0) {
            System.out.println(menuMensagem);
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPorTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void buscarLivroPorTitulo() {
        System.out.println("Insira o nome do livro que você deseja procurar");
        var tituloLivro = scanner.nextLine().trim();
        var responseDTO = gutendexApi.buscarLivroPorTitulo(tituloLivro);

        // Verifica se a lista de livros está vazia
        if (responseDTO.livros().isEmpty()) {
            System.out.println("Livro não encontrado.");
            return;
        }

        // Pega somente o primeiro livro
        var primeiroLivroDTO = responseDTO.livros().getFirst();

        // Verifica se o livro já existe no banco para não duplicar
        if (livroRepository.existsByTitulo(primeiroLivroDTO.titulo())) {
            System.out.println("O livro já está cadastrado no banco de dados.");
            return;
        }

        Livro livro = new Livro();
        livro.setTitulo(primeiroLivroDTO.titulo());
        livro.setDownloadCount(primeiroLivroDTO.numeroDownloads());
        livro.setIdiomas(new HashSet<>(primeiroLivroDTO.idiomas()));

        Set<Autor> autores = new HashSet<>();
        for (AutorDTO autorDTO : primeiroLivroDTO.autores()) {
            Autor autor = autorRepository.findByNome(autorDTO.nome());

            if (autor == null) {
                // Se não existir, cria um novo
                autor = new Autor();
                autor.setNome(autorDTO.nome());
                autor.setAnoNascimento(autorDTO.anoNascimento());
                autor.setAnoFalecimento(autorDTO.anoFalecimento());
            }
            autores.add(autor);
        }
        livro.setAutores(autores);

        livroRepository.save(livro);

        exibirDetalhesLivro(livro);
    }

    private void exibirDetalhesLivro(Livro livro) {
        String autoresNomes = livro.getAutores().stream()
                .map(Autor::getNome)
                .collect(Collectors.joining(", "));
        String idiomas = String.join(", ", livro.getIdiomas());
        System.out.printf("""
                \n----- LIVRO -----
                Título: %s
                Autor: %s
                Idioma: %s
                Número de downloads: %d
                -----------------
                """, livro.getTitulo(), autoresNomes, idiomas, livro.getDownloadCount());
    }

    private void listarLivrosRegistrados() {
        List<Livro> livros = livroRepository.findAll();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro registrado.");
            return;
        }

        System.out.println("Livros registrados:");
        livros.forEach(this::exibirDetalhesLivro);
    }


    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado.");
            return;
        }

        System.out.println("Autores registrados:");
        autores.forEach(this::exibirDetalhesAutor);
    }
    
    private void exibirDetalhesAutor(Autor autor) {
        String livrosAutor = autor.getLivros().stream()
                .map(Livro::getTitulo)
                .collect(Collectors.joining(", "));

        System.out.printf("""
                        \n----- AUTOR -----
                        Autor: %s
                        Ano de nascimento: %d
                        Ano de falecimento: %d
                        Livros: [%s]
                        -----------------
                        """, autor.getNome(), autor.getAnoNascimento()
                , autor.getAnoFalecimento(), livrosAutor);
    }
}
