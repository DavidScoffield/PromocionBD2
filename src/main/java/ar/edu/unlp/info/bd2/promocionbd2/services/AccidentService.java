package ar.edu.unlp.info.bd2.promocionbd2.services;

import ar.edu.unlp.info.bd2.promocionbd2.entity.Accident;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface AccidentService {

    /**
     * Parsea el CSV y almacena la informacion en las bases de datos.
     * @param path la ruta donde se encuentra guardado el CSV de Accidentes
     * */
    public void uploadCSV(String path) throws IOException;

    /**
     * Retorna los accidentes ocurridos entre dos fechas
     * @param start Fecha de comienzo del intervalo
     * @param end Fecha de fin del intervalo
     * @return lista de Accident
     */
    public List<Accident> getAccidentsBetweenDates(String start, String end) throws ParseException;

    /**
     * Retorna los accidentes ocurridos a cierta distancia de un punto geografico
     * @param longitude Coordenada correspondiente a la longitud del punto geografico
     * @param latitude Coordenada correspondiente a la latitud del punto geografico
     * @param radius Radio de busqueda
     * @return lista de Accident
     */
    public List<Accident> getAccidentsNearLocation(Double longitude, Double latitude, Double radius) throws Exception;
}
