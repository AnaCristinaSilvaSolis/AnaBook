package com.ana.anaBook.Service;

import com.ana.anaBook.Models.Libro;

import java.io.IOException;

public interface DataConverter {
    Libro convertJsonToLibro(String json) throws IOException;
}