package com.ana.anaBook.Service;

import com.ana.anaBook.Models.Autor;
import com.ana.anaBook.Models.Libro;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GutendexConverter implements DataConverter {

    private final ObjectMapper objectMapper;

    public GutendexConverter() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Libro convertJsonToLibro(String json) throws IOException {
        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode bookNode = rootNode.path("results").get(0);

        List<Autor> autores = new ArrayList<>();
        for (JsonNode authorNode : bookNode.path("authors")) {
            String nombre = authorNode.path("name").asText();
            Integer anoNacimiento = authorNode.path("birth_year").isInt() ? authorNode.path("birth_year").asInt() : null;
            Integer anoFallecimiento = authorNode.path("death_year").isInt() ? authorNode.path("death_year").asInt() : null;
            autores.add(new Autor(nombre, anoNacimiento, anoFallecimiento));
        }

        String titulo = bookNode.path("title").asText();
        List<String> idiomas = new ArrayList<>();
        bookNode.path("languages").forEach(languageNode -> idiomas.add(languageNode.asText()));
        Integer numeroDescargas = bookNode.path("download_count").asInt();

        return new Libro(titulo, autores, idiomas, numeroDescargas);
    }
}
