package com.alura.literalura.model;


import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosLibro(@JsonAlias() String titulo,
                         @JsonAlias() ) {
}
