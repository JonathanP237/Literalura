package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique=true)
    private String nombre;
    private String fechaNacimiento;
    private String fechaMuerte;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(){}

    public Autor(DatosAutor datos){
        this.nombre = datos.nombre();
        this.fechaNacimiento = datos.fechaNacimiento();
        this.fechaMuerte = datos.fechaMuerte();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFechaMuerte() {
        return fechaMuerte;
    }

    public void setFechaMuerte(String fechaMuerte) {
        this.fechaMuerte = fechaMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void addLibro(Libro libro) {
        this.libros.add(libro);
    }
}
