package com.alura.literalura.principal;

import com.alura.literalura.dto.AutorDTO;
import com.alura.literalura.service.GutendexAPI;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private final Scanner scanner = new Scanner(System.in);
    private final GutendexAPI gutendexApi = new GutendexAPI();

    public void exibeMenu() {
        int opcao = -1;
        String menuMensagem = """
                \n------------------------------
                Escolha o número da opção:
                1 - Buscar livro pelo título
                
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
        var primeiroLivro = responseDTO.livros().getFirst();

        var autoresDoLivro = primeiroLivro.autores().stream()
                .map(AutorDTO::nome)
                .collect(Collectors.joining(", "));
        var idiomas = String.join(", ", primeiroLivro.idiomas());


        System.out.printf("""
                ---- LIVRO -----
                Título: %s
                Autor: %s
                Idioma: %s
                Número de downloads: %d
                ----------------
                """, primeiroLivro.titulo(), autoresDoLivro, idiomas, primeiroLivro.numeroDownloads());
    }
}
