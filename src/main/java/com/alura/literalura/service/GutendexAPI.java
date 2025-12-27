package com.alura.literalura.service;

import com.alura.literalura.dto.GutendexResponseDTO;
import com.alura.literalura.http.ConsumoApi;
import com.alura.literalura.mapper.JsonMapper;

public class GutendexAPI {
    private static final String BASE_URL = "https://gutendex.com/books/";
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final JsonMapper jsonMapper = new JsonMapper();

    public GutendexResponseDTO buscarLivroPorTitulo(String titulo) {
        String endpoint = BASE_URL + "?search=" + titulo.replace(" ", "%20");
        var json = consumoApi.fazerRequisicao(endpoint);
        return jsonMapper.fromJson(json, GutendexResponseDTO.class);
    }
}
