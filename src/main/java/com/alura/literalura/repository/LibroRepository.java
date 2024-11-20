package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Libro findByTitulo(String nombre);
    @Query("SELECT l from Libro l WHERE l.idiomas ILIKE %:idiomaIngresado%")
    List<Libro> librosPorIdioma(String idiomaIngresado);
}
