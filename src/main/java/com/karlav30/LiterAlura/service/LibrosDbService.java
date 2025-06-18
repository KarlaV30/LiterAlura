package com.karlav30.LiterAlura.service;

import com.karlav30.LiterAlura.LibroDTO;
import com.karlav30.LiterAlura.model.AutorModel;
import com.karlav30.LiterAlura.model.LibrosModel;
import com.karlav30.LiterAlura.repository.AutorRepository;
import com.karlav30.LiterAlura.repository.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio encargado de la lógica de persistencia de libros y autores.
 *
 * Funcionalidad:
 * - Verifica si el autor ya existe en la base de datos mediante su nombre.
 *   - Si existe, asocia el libro al autor existente.
 *   - Si no existe, crea un nuevo autor con los datos del DTO y lo guarda.
 * - Crea una instancia de LibrosModel con los datos del DTO y la asocia al autor.
 * - Guarda el libro en la base de datos.
 *
 * Uso de dependencias:
 * - AutorRepository: para buscar o crear autores.
 * - LibrosRepository: para guardar libros asociados a autores.
 */
@Service
public class LibrosDbService {


    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LibrosRepository librosRepository;

    //constructor
    public LibrosDbService(LibrosRepository repository, AutorRepository autorRepository) {
        this.librosRepository= repository;
        this.autorRepository = autorRepository;
    }

    public void guardarLibroDesdeDTO(LibroDTO dto){

        Optional<AutorModel> autorExistente = autorRepository.findByNombre(dto.nombreAutor());

        AutorModel autor;
        if(autorExistente.isPresent()){
            autor = autorExistente.get();
        }else {
            autor = new AutorModel();
            autor.setNombre(dto.nombreAutor());
            autor.setFecha_nacimiento(dto.fechaNacimientoAutor());
            autor.setFecha_muerte(dto.fechaMuerteAutor());
            autor=autorRepository.save(autor);
        }

        if (librosRepository.existsByTituloAndAutor_Nombre(dto.titulo(), dto.nombreAutor())) {
            System.out.println("⚠️ El libro '" + dto.titulo() + "' del autor '" + dto.nombreAutor() + "' ya está registrado.");
            return; // Salimos del método para evitar guardar duplicado
        }
        LibrosModel libro = new LibrosModel();
        libro.setTitulo(dto.titulo());
        libro.setIdioma(dto.idioma());
        libro.setNumero_descargas(dto.numeroDescargas());
        libro.setAutor(autor); // Relación entre libro y autor



        librosRepository.save(libro);
        System.out.println("¡Libro guardado con exito en la base de datos! :)");


    }

}
