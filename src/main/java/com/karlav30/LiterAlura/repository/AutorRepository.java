package com.karlav30.LiterAlura.repository;

import com.karlav30.LiterAlura.model.AutorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/*
* aqui puedo accesar a los datos en la entidad autor
* uso metodos personalizados:
* findByNombre recupera el nombrre del auror y lo guarda en esa lista generica
*/
public interface AutorRepository extends JpaRepository<AutorModel, Long> {
    Optional<AutorModel> findByNombre(String nombre);
}



