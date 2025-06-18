package com.karlav30.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


//recopilo los datos que quiero usar de los libros, unicamente los declarados
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(

        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<Autor> autor,
        @JsonAlias("summaries") List<String> summaries,
        @JsonAlias("languages") List<String> lenguages,
        @JsonAlias("download_count") int numero_descargas
) {}

