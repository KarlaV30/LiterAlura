package com.karlav30.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

//En este modelo recupero los datos que me interesan de un autor o autores, esto desde la api
@JsonIgnoreProperties(ignoreUnknown = true)
public record Autor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year")String fecha_nacimiento,
        @JsonAlias("death_year")String fecha_fallecimiento


        ) {
}
