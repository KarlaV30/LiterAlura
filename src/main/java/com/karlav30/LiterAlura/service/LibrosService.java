package com.karlav30.LiterAlura.service;

import com.karlav30.LiterAlura.LibroDTO;
import com.karlav30.LiterAlura.model.*;
import com.karlav30.LiterAlura.repository.AutorRepository;
import com.karlav30.LiterAlura.repository.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Servicio que gestiona operaciones relacionadas con la búsqueda,
 * transformación y persistencia de información de libros.
 *
 * Funcionalidades:
 * - Consulta libros desde una API externa usando el título.
 * - Convierte los datos obtenidos a objetos DTO.
 * - Almacena libros en una lista temporal para procesamientos posteriores.
 * - Consulta y filtra autores según condiciones específicas.
 * - Consulta y cuenta libros persistidos en base de datos según el idioma.
 *
 * Utiliza:
 * - LibrosRepository para interacciones con la base de datos.
 * - ConsumoApi y ConvierteDatos para consultar y transformar datos externos.
 */
@Service
public class LibrosService {
    private final List<DatosLibros> baseLibros = new ArrayList<>();
    @Autowired
    private LibrosRepository librosRepository;
    @Autowired
    private AutorRepository autorRepository;

    /**
     * Realiza una búsqueda por título en la API de Gutendex
     * y almacena el primer resultado encontrado en memoria.
     */
    public Optional<DatosLibros> buscarLibroPorTitulo(String titulo, Datos datos) {
        String tituloFormateado = titulo.trim().replace(" ", "+");
        String urlBusqueda = "https://gutendex.com/books/?search=" + tituloFormateado;

        String jsonBusqueda = new ConsumoApi().obtenerDatos(urlBusqueda);
        Datos datosBusqueda = new ConvierteDatos().obtenerDatos(jsonBusqueda, Datos.class);

        Optional<DatosLibros> libroEncontrado = datosBusqueda.resultados().stream().findFirst();
        libroEncontrado.ifPresent(libro -> baseLibros.add(libro));
        return libroEncontrado;
    }

    /**
     * Busca un libro por título y lo convierte en un DTO
     * con los datos relevantes del autor y el libro.
     */
    public Optional<LibroDTO> buscarYConvertirLibro(String titulo, Datos datos) {
        Optional<DatosLibros> libroEncontrado = buscarLibroPorTitulo(titulo, datos);

        if (libroEncontrado.isPresent()) {
            DatosLibros libro = libroEncontrado.get();
            return Optional.of(new LibroDTO(
                    libro.titulo(),
                    libro.lenguages().get(0),
                    libro.numero_descargas(),
                    libro.autor().get(0).nombre(),
                    Integer.parseInt(libro.autor().get(0).fecha_nacimiento()),
                    libro.autor().get(0).fecha_fallecimiento() != null ? Integer.parseInt(libro.autor().get(0).fecha_fallecimiento()) : 0
            ));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Recupera todos los libros almacenados en la base de datos.
     */
    public List<LibrosModel> obtenerTodosLosLibros() {
        return librosRepository.findAll();
    }

    /**
     * Devuelve una lista de autores únicos extraídos de los libros almacenados en memoria.
     */
    public List<AutorModel> listarAutores() {
        return autorRepository.findAll();
    }

    /**
     * Filtra autores que estaban vivos en un año específico según fechas de nacimiento y fallecimiento.
     */
    public List<AutorModel> listarAutoresPorAñoVivos(int año) {
        List<AutorModel> autores = autorRepository.findAll();
        List<AutorModel> autoresVivos = new ArrayList<>();

        for (AutorModel autor : autores) {
            boolean nacioAntes = autor.getFecha_nacimiento() <= año;
            boolean murioDespues = autor.getFecha_muerte() == 0 || autor.getFecha_muerte() > año;

            if (nacioAntes && murioDespues) {
                autoresVivos.add(autor);
            }
        }

        return  autorRepository.findAll().stream()
                .filter(autor -> autor.getFecha_nacimiento() <= año &&
                        (autor.getFecha_muerte() == 0 || autor.getFecha_muerte() > año))
                .toList();

    }

    /**
     * Cuenta cuántos libros están registrados en la base de datos con el idioma indicado.
     */
    public long contarLibrosPorIdioma(String idioma) {
        return librosRepository.countByIdioma(idioma);
    }

    /**
     * Devuelve la lista de libros que coinciden con un idioma específico desde la base de datos.
     */
    public List<LibrosModel> obtenerLibroPorIdioma(String idioma) {
        return librosRepository.findByIdioma(idioma);
    }

    public List<String> obtenerLibrosPorListaIdioma() {
        return obtenerTodosLosLibros().stream()
                .map(libro ->libro.getIdioma())
                .distinct()
                .collect(Collectors.toList());
    }



}