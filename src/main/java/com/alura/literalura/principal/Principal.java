package com.alura.literalura.principal;

import com.alura.literalura.model.Datos;
import com.alura.literalura.model.DatosLibro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ConvierteDatos;
import org.hibernate.boot.model.internal.XMLContext;

import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/?search=";
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositoryLibros;
    private AutorRepository repositoryAutores;

    public Principal(LibroRepository repositoryLibros, AutorRepository repositoryAutores){
        this.repositoryLibros = repositoryLibros;
        this.repositoryAutores = repositoryAutores;
    }

    public void mostrarMenu() {
        var opcion = "-1";
        while (!opcion.equals("0")) {
            var menu = """
                    1. Buscar libro por titulo
                    2. Listar libros registrados
                    3. Listar autores registrados
                    4. Listar autores vivos en un determinado año
                    5. Listar libros por idioma
                    0. salir
                    """;
            System.out.println(menu);
            opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    buscarLibroPorTitulo();
                    break;
                default:
                    System.out.println("Ingrese una opción valida");
                    break;
            }
        }
    }

    private Optional<DatosLibro> getDatosLibro() {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        var nombreLibro = scanner.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obternerDatos(json, Datos.class);
        Optional<DatosLibro> libroBuscado = datosBusqueda.libros().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();
        return libroBuscado;
    }

    private void buscarLibroPorTitulo() {
        var libroBuscado = getDatosLibro();
        if(libroBuscado.isPresent()){
            var libro = libroBuscado.get();
            var autores = libro.autor().stream()
                    .map(a -> a.nombre())
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("N/A");
            System.out.printf("""
                ------------------------------------------------------------
                Libro Encontrado:
                Titulo: %s
                Autor(es): %s
                Idiomas: %s
                ------------------------------------------------------------
                """, libro.titulo(), autores, String.join(", ", libro.idiomas()));
        } else {
            System.out.println("Libro no encontrado");
        }
    }
}