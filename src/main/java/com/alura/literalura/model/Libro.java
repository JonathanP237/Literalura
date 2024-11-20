package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique=true)
    private String titulo;
    @ManyToOne
    private Autor autor;
    private String idiomas;

    public Libro(){}

    public Libro(DatosLibro datos){
        this.titulo = datos.titulo();
        this.idiomas = datos.idiomas().stream()
                .reduce((a, b) -> a + ", " + b)
                .orElse("N/A");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }
}
