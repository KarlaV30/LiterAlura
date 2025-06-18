package com.karlav30.LiterAlura.repository;

import com.karlav30.LiterAlura.model.LibrosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/*
* aqui puedo accesar a los datos en la entidad libros
* uso metodos personalizados:
* - countByIdioma(String idioma): Devuelve el número total de libros que pertenecen a un idioma específico.
* - findByIdioma(String idioma): Recupera una lista de libros filtrados por idioma.

 */

@Repository
public interface LibrosRepository extends JpaRepository<LibrosModel, Long> {
    Long countByIdioma(String idioma);

    List<LibrosModel> findByIdioma(String idioma);

    boolean existsByTituloAndAutor_Nombre(String titulo, String nombre);



}
