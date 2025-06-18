package com.karlav30.LiterAlura.principal;

import com.karlav30.LiterAlura.LibroDTO;
import com.karlav30.LiterAlura.model.AutorModel;
import com.karlav30.LiterAlura.model.Datos;
import com.karlav30.LiterAlura.model.LibrosModel;
import com.karlav30.LiterAlura.service.ConsumoApi;
import com.karlav30.LiterAlura.service.ConvierteDatos;
import com.karlav30.LiterAlura.service.LibrosDbService;
import com.karlav30.LiterAlura.service.LibrosService;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books/";

    private final ConsumoApi consumoApi = new ConsumoApi();
    private final ConvierteDatos convierteDatos = new ConvierteDatos();
    private final LibrosService librosService;
    private final LibrosDbService libroDbService;



    public Principal(LibrosService librosService, LibrosDbService libroDbService) {

        this.librosService = librosService;
        this.libroDbService = libroDbService;

    }

    //metodo para mostrar el menu y su logica
    public void muestraMenu() {


        //declaro esta variable para poder usar la url base
        var json = consumoApi.obtenerDatos(URL_BASE);
        var datos = convierteDatos.obtenerDatos(json, Datos.class);

        System.out.println("\n************Bienvenidos a la biblioteca LiterAlura************");

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {

            while(true){
            //menu para usuario
            System.out.println("\n MENÚ:");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Mostrar lista de libros guardados");
            System.out.println("3. Filtrar libros por idioma");
            System.out.println("4. Mostrar Lista De Autores");
            System.out.println("5. Listar autores Vivos En Un Determinado Año ");
            System.out.println("8. Salir");
            System.out.print("Elige una opción del Menu: ");
                try {
                    opcion = scanner.nextInt();
                    if (opcion < 0 || opcion > 8) {
                        System.out.println("Por favor, elige una opción válida del menú.\n");
                    } else {
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada no válida. Debes ingresar un número que este en el Menu.\n");
                    scanner.nextLine(); // limpia el buffer de entrada
                }
            }

            scanner.nextLine();


            //Manejo para cada uno de las opciones dell menu
            switch (opcion) {

                //solicito al usuario por consola, busca el titulo en el dto si esta lo imprime segun formato
                case 1 -> {
                    System.out.print("Introduce el título: ");
                    String titulo = scanner.nextLine();

                    if (!esTituloValido(titulo)) {
                        System.out.println("Eso no parece un título válido. Intenta escribir un nombre más descriptivo.");
                        break;
                    }
                    Optional<LibroDTO> libroDTO = librosService.buscarYConvertirLibro(titulo, datos);

                    if (libroDTO.isPresent()) {
                        System.out.println("\n**Libro encontrado:**");
                        System.out.println("Título: " + libroDTO.get().titulo());
                        System.out.println("Idioma: " + libroDTO.get().idioma());
                        System.out.println("Descargas: " + libroDTO.get().numeroDescargas());
                        System.out.println("Autor: " + libroDTO.get().nombreAutor());

                        // Guardar en la base de datos luego de inprimirlos en consola
                        libroDbService.guardarLibroDesdeDTO(libroDTO.get());

                    } else {
                        System.out.println("Libro no encontrado. Intenta con otro título.");
                    }
                }

                //trae lo que este en la base de datos, y lo imprime segun formato dado
                case 2 -> {
                    List<LibrosModel> librosGuardados = librosService.obtenerTodosLosLibros();
                        System.out.println("\n Libros guardados en la base de datos:");

                        librosGuardados.forEach(libro -> System.out.println(
                                " Título: " + libro.getTitulo() +
                                " | Idioma: " + libro.getIdioma() +
                                " | Descargas: " + libro.getNumero_descargas()+
                                "| Autor: " + libro.getAutor().getNombre()));
                    System.out.println("\n Total de libros guardados: " + librosGuardados.size() + "\n");
                }

                //pide el dioma en el que se desea buscar un libro, mientras este en la base de datos
                case 3 -> {
                    List<String> listaIdiomas = librosService.obtenerLibrosPorListaIdioma();
                        for (int i = 0; i < listaIdiomas.size(); i++) {
                            System.out.println((i + 1) + ". " + listaIdiomas.get(i));
                        }

                    System.out.print("Introduce una opcion: ");
                    String idiomaSeleccionado = scanner.next();

                        idiomaSeleccionado = listaIdiomas.get(Integer.parseInt(idiomaSeleccionado) - 1);

                    System.out.println("La base de datos cuenta con los siguientes titulos en "+idiomaSeleccionado+"\n");
                        librosService.obtenerLibroPorIdioma(idiomaSeleccionado)
                        .forEach(libro -> System.out.println(

                                "Título: " + libro.getTitulo() +
                                        " del | Autor: " + libro.getAutor().getNombre()));

//                    long cantidadLibros = librosService.contarLibrosPorIdioma(idioma);
//
//                    System.out.println("\nCantidad de libros en idioma '" + idioma + "': " + cantidadLibros);


                }

                //devuelve una lista de los autores
                case 4 -> {
                    List<AutorModel> autores = librosService.listarAutores();
                    int i = 1;
                    for (AutorModel autor : autores) {
                        System.out.println(i++ + ". " + autor.getNombre());
                    }

                }

                //solicita por consola un año para saber si esta vivo en un determinado tiempo
                case 5 -> {
                    System.out.println("Ingrese el año para saber si un autor estaba vivo: ");
                    int año = scanner.nextInt();
                    scanner.nextLine();
                    List<AutorModel> autoresVivos = librosService.listarAutoresPorAñoVivos(año);
                    int i = 1;
                    for (AutorModel autor : autoresVivos) {
                        System.out.println(i++ + ". " + autor);
                    }

                }

                //maneja la salida del siclo y cierra la applocacion
                case 8 -> System.out.println("¡Hasta luego!");
                default -> System.out.println("Opción inválida");
            }
        } while (opcion != 8);

        scanner.close();
    }

    //este metodo auxiliar me ayuda en caso de que al pedir el titulo de un libro para buscar ingresen un caracter o numero
    private boolean esTituloValido(String input) {
        var tituloFiltrado = input.trim();
        return tituloFiltrado.matches(".*[a-zA-Z].*");
    }
}