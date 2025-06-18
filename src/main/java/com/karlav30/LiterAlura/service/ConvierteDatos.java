package com.karlav30.LiterAlura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * Implementación de la interfaz IConvierteDatos que convierte cadenas JSON en objetos Java.
 * Utiliza la biblioteca Jackson (ObjectMapper) para deserializar datos JSON en instancias de clases específicas.
 * Método:
 * - <T> obtenerDatos(String json, Class<T> clase): Convierte un JSON a una instancia del tipo especificado.
 */

 public class ConvierteDatos implements IConvierteDatos{

    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json,clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
