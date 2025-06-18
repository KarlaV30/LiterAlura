package com.karlav30.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


//este es un recor que maneja todos los datos que vienen ddesde la api y los almacena en una lista para poder trabajar con ellos
@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos(
        @JsonAlias("results") List<DatosLibros> resultados
) {
}
