package com.alura.literalura.principal;

import com.alura.literalura.model.DatosLibro;
import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ConvierteDatos;
import org.hibernate.boot.model.internal.XMLContext;

import java.util.Scanner;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvierteDatos conversor = new ConvierteDatos();

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
    private DatosLibro getDatosLibro(){
        return null;
    }
    private void buscarLibroPorTitulo(){
        var json = consumoApi.obtenerDatos(URL_BASE);
        System.out.println(json);
    }
}