package com.bootcampqualitychallenge.exception;

public class NeighborhoodNotFound extends Exception{
    public NeighborhoodNotFound(String name) {
        super(String.format("Não foi possível encontrar um bairro com nome %s na nossa base de dados.", name));
    }
}
