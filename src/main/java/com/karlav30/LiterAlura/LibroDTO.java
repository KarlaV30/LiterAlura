package com.karlav30.LiterAlura;

/**
 * Record que representa un Data Transfer Object (DTO) para la entidad Libro.
 *
 * Este DTO facilita el intercambio de datos entre la capa de servicio y otras capas
 * (como controladores o vistas), sin exponer directamente la entidad persistente.
 */
public record LibroDTO(
        String titulo,
        String idioma,
        int numeroDescargas,
        String nombreAutor,
        int fechaNacimientoAutor,
        int fechaMuerteAutor

) {
}
