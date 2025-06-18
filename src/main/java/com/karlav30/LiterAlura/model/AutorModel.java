package com.karlav30.LiterAlura.model;

import jakarta.persistence.*;

import java.util.List;

//crea la entidad y la tabla en la base de datos, manejando solo los datos declarados con un id unico para cada autor
@Entity
@Table(name = "autor")
public class AutorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private int fecha_nacimiento;
    private int fecha_muerte;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    private List<LibrosModel> librosPorAutor;

    public AutorModel() {}

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

    public int getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(int fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public int getFecha_muerte() {
        return fecha_muerte;
    }

    public void setFecha_muerte(int fecha_muerte) {
        this.fecha_muerte = fecha_muerte;
    }



    @Override
    public String toString() {
        return nombre + " (Nacido en " + fecha_nacimiento +
                ", Fallecido en " + (fecha_muerte == 0 ? "Vivo" : fecha_muerte) + ")";
    }

}
