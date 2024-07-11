package com.ana.anaBook.Principal;

import com.ana.anaBook.Models.Libro;
import com.ana.anaBook.Repository.AutorRepository;
import com.ana.anaBook.Repository.LibroRepository;
import com.ana.anaBook.Service.GutendexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
public class Menu {

    @Autowired
    private GutendexService gutendexService;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Listar todos los libros");
            System.out.println("3. Listar todos los autores");
            System.out.println("4. Listar autores vivos en determinado año");
            System.out.println("5. Exhibir cantidad de libros en un determinado idioma");
            System.out.println("0. Salir");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    System.out.println("Ingrese el título del libro:");
                    String titulo = scanner.nextLine();
                    try {
                        Libro libro = gutendexService.buscarLibroPorTitulo(titulo);
                        libroRepository.save(libro);
                        System.out.println("Libro guardado: " + libro);
                    } catch (Exception e) {
                        System.out.println("Error al buscar el libro: " + e.getMessage());
                    }
                    break;
                case 2:
                    libroRepository.findAll().forEach(System.out::println);
                    break;
                case 3:
                    autorRepository.findAll().forEach(System.out::println);
                    break;
                case 4:
                    System.out.println("Ingrese el año:");
                    int ano = scanner.nextInt();
                    autorRepository.findByAnoFallecimientoIsNullAndAnoNacimientoLessThanEqual(ano)
                            .forEach(System.out::println);
                    break;
                case 5:
                    System.out.println("Ingrese el idioma (por ejemplo, 'en' o 'es'):");
                    String idioma = scanner.nextLine();
                    libroRepository.findByIdiomasContains(idioma).forEach(System.out::println);
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}

