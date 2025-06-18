package com.karlav30.LiterAlura.model;

import jakarta.persistence.*;

//crea la entidad y la tabla para la base de datos, maneja relaciones entre libro y autor por su id
@Entity
@Table(name = "libros")

public class LibrosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String idioma;
    private int numero_descargas;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private AutorModel autor;

    public AutorModel getAutor() {
        return autor;
    }

    public void setAutor(AutorModel autor) {
        this.autor = autor;
    }

    public LibrosModel() {

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

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getNumero_descargas() {
        return numero_descargas;
    }

    public void setNumero_descargas(int numero_descargas) {
        this.numero_descargas = numero_descargas;
    }

}
