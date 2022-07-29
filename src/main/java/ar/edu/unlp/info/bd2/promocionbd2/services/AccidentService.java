package ar.edu.unlp.info.bd2.promocionbd2.services;

import ar.edu.unlp.info.bd2.promocionbd2.model.Accident;
import org.springframework.data.geo.Point;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
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

    /**
     * Retorna la distancia promedio desde el inicio al fin del accidente
     * @return distancia promedio
     */
    public Double getAverageDistance();

    /**
     * Retorna los n puntos más peligrosos dentro de un determinado radio
     * @param radius Radio de busqueda
     * @param amount Cantidad de puntos a buscar
     * @return lista de Point
     */
    /*TODO check parameters*/
    public List<Point> getMostDangerousPoints(Double radius, Integer amount);

    /**
     * Retorna una lista con los nombres de las 5 calles con mas accidentes
     * @return lista de String
     */
    public List<String> getFiveStreetsWithMostAccidents();

    /**
     * Retorna las condiciones más comunes en los accidentes
     * @return un HashMap con las claves "commonAccidentWeatherCondition" (la condición climática más común) y "commonAccidentStartHour" (la hora de comienzo más común)
     */
    HashMap<String, Object> getCommonAccidentConditions();
}
