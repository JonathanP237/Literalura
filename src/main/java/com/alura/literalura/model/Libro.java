package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String titulo;
    @ManyToOne
    private Autor autor;
    private String idiomas;
    private Double numeroDescargas;

    public Libro(){}

    public Libro(DatosLibro datos, Autor autor) {
        if(datos.titulo().length()>255){
            this.titulo = datos.titulo().substring(0, 255);
        }else{this.titulo = datos.titulo();};
        this.autor = autor;
        this.idiomas = datos.idiomas().stream()
                .reduce((a, b) -> a + ", " + b)
                .orElse("N/A");
        this.numeroDescargas = datos.numeroDescargas();
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

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return
                " Titulo= " + titulo + '\n' +
                " Autor= " + autor.getNombre() + '\n' +
                " Idiomas= " + idiomas + '\n' +
                " Descargas= " + numeroDescargas;
    }
}
