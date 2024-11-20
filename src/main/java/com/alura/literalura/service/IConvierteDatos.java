package com.alura.literalura.service;

public interface IConvierteDatos {
    <T> T obternerDatos(String json, Class<T> clase);
}
