package com.karlav30.LiterAlura.service;

/*
 * Interfaz genérica para convertir datos en formato JSON a objetos Java.
 * Método:
 * - <T> obtenerDatos(String json, Class<T> clase): Define una operación genérica que
 *   transforma una cadena JSON en una instancia del tipo especificado.
 */
public interface IConvierteDatos {


    <T> T obtenerDatos(String json, Class<T> clase);
}
