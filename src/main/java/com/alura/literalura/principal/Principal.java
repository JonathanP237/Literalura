package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ConvierteDatos;
import org.hibernate.boot.model.internal.XMLContext;

import java.util.*;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/?search=";
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositoryLibros;
    private AutorRepository repositoryAutores;

    public Principal(LibroRepository repositoryLibros, AutorRepository repositoryAutores) {
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
                case "2":
                    mostrarLibrosBuscados();
                    break;
                case "3":
                    mostrarAutores();
                    break;
                case "4":
                    mostrarAutoresVivos();
                    break;
                case "5":
                    mostrarLibrosIdioma();
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
        Optional<DatosLibro> libroBuscado = datosBusqueda.libros().stream().filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase())).findFirst();
        return libroBuscado;
    }

    private void buscarLibroPorTitulo() {
        var libroBuscado = getDatosLibro();
        if (libroBuscado.isPresent()) {
            var libro = libroBuscado.get();
            var autores = libro.autor().stream().map(a -> a.nombre()).reduce((a, b) -> a + ", " + b).orElse("N/A");
            System.out.printf("""
                    ------------------------------------------------------------
                    Libro Encontrado:
                    Titulo: %s
                    Autor(es): %s
                    Idiomas: %s
                    Descargas: %s
                    ------------------------------------------------------------
                    """, libro.titulo(), autores, String.join(", ", libro.idiomas()), libro.numeroDescargas());
            Autor autor = new Autor(libro.autor().get(0));
            if (repositoryAutores.findByNombre(autor.getNombre()) == null) {
                repositoryAutores.save(autor);
            }
            if (repositoryLibros.findByTitulo(libro.titulo()) == null) {
                repositoryLibros.save(new Libro(libro, autor));
            }
        } else {
            System.out.println("Libro no encontrado");
        }
    }

    private void mostrarLibrosBuscados() {
        List<Libro> libros = repositoryLibros.findAll();

        libros.stream().sorted(Comparator.comparing(Libro::getTitulo)).forEach(System.out::println);
    }

    private void mostrarAutores() {
        List<Autor> autores = repositoryAutores.findAll();
        List<Libro> libros = repositoryLibros.findAll();

        for (Autor autor : autores) {
            List<Libro> librosAutor = new ArrayList<>();
            for (Libro libro : libros) {
                if (libro.getAutor().getId().equals(autor.getId())) {
                    librosAutor.add(libro);
                }
            }
            autor.setLibros(librosAutor);
        }

        autores.stream().sorted(Comparator.comparing(Autor::getNombre)).forEach(System.out::println);
    }

    private void mostrarAutoresVivos(){
        System.out.println("Ingrese el año del que desea validar los autores:");
        var validarFecha = scanner.nextLine();
        List<Autor> autoresVivos = repositoryAutores.findByFechaIngresa(validarFecha);
        autoresVivos.forEach(System.out::println);
    }

    private void mostrarLibrosIdioma(){
        System.out.println("""
                Ingrese el idioma:
                es - Español, en - Ingles, fr - Frances, pt - Portugues
                """);
        var idioma = scanner.nextLine();
        List<Libro> librosPorIdioma = repositoryLibros.librosPorIdioma(idioma.toLowerCase().substring(0, 2));
        librosPorIdioma.forEach(System.out::println);
    }
}