package com.ana.anaBook.Service;

import com.ana.anaBook.Models.Autor;
import com.ana.anaBook.Models.Libro;
import com.ana.anaBook.Repository.LibroRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GutendexService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final LibroRepository libroRepository;

    public GutendexService(LibroRepository libroRepository) {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.libroRepository = libroRepository;
    }

    @Transactional
    public Libro buscarLibroPorTitulo(String titulo) throws IOException {
        String url = "https://gutendex.com/books?title=" + titulo;
        String response = restTemplate.getForObject(url, String.class);
        JsonNode rootNode = objectMapper.readTree(response);
        JsonNode bookNode = rootNode.path("results").get(0);

        List<Autor> autores = new ArrayList<>();
        for (JsonNode authorNode : bookNode.path("authors")) {
            Autor autor = new Autor();
            autor.setNombre(authorNode.path("name").asText());
            autor.setAnoNacimiento(authorNode.path("birth_year").isInt() ? authorNode.path("birth_year").asInt() : null);
            autor.setAnoFallecimiento(authorNode.path("death_year").isInt() ? authorNode.path("death_year").asInt() : null);
            autores.add(autor);
        }

        Libro libro = new Libro();
        libro.setTitulo(bookNode.path("title").asText());
        libro.setAutores(autores);

        List<String> idiomas = new ArrayList<>();
        bookNode.path("languages").forEach(languageNode -> idiomas.add(languageNode.asText()));
        libro.setIdiomas(idiomas);

        libro.setNumeroDescargas(bookNode.path("download_count").asInt());

        // Guardar el libro en la base de datos
        libroRepository.save(libro);

        return libro;
    }
}
