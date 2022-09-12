package ar.edu.unlp.info.bd2.promocionbd2.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import ar.edu.unlp.info.bd2.promocionbd2.dto.NearAccidentsSeverityRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.model.Accident;

public interface AccidentService {

    /**
     * Retorna los accidentes ocurridos entre dos fechas
     * @param start fecha de comienzo del intervalo
     * @param end fecha de fin del intervalo
     * @param page página solicitada
     * @param perPage cantidad de elementos por página
     * @return un HashMap con una clave que posee información sobre la página y otra clave con el resultado obtenido
     */
    public HashMap<Object, Object> getAccidentsBetweenDates(String start, String end, int page, int perPage) throws Exception;

    /**
     * Retorna los accidentes ocurridos a cierta distancia de un punto geografico
     * @param longitude coordenada correspondiente a la longitud del punto geografico
     * @param latitude coordenada correspondiente a la latitud del punto geografico
     * @param radius radio de busqueda
     * @return lista de Accident
     */
    public List<Accident> getAccidentsNearLocation(Double longitude, Double latitude, Double radius) throws Exception;

    /**
     * Retorna la distancia promedio desde el inicio al fin del accidente
     * @return distancia promedio
     * @throws Exception
     */
    HashMap<String, Object> getAverageDistance() throws Exception;

    /**
     * Retorna los N puntos más peligrosos dentro de un determinado radio
     * @param radius radio de búsqueda
     * @param amount cantidad de puntos a buscar
     * @return lista que indica el punto, la cantidad de accidentes en el punto, 
     *         la cantidad de accidentes cercanos y la severidad total de los accidentes
     */
    public Collection<NearAccidentsSeverityRepresentation> getMostDangerousPoints(Double radius, Integer amount) throws Exception;

    /**
     * Retorna una lista con los nombres de las 5 calles con mas accidentes
     * @return lista de String
     */
    public List<String> getFiveStreetsWithMostAccidents() throws Exception;

    /**
     * Retorna las condiciones más comunes en los accidentes
     * @return un HashMap con las claves "commonAccidentWeatherCondition", "commonAccidentWindDirection",
     *          "commonAccidentHumidity", "commonAccidentVisibility" y "commonAccidentStartHour"
     */
    HashMap<String, Object> getCommonAccidentConditions() throws Exception;

    /**
     * Retorna la distancia promedio (en km) de cada accidente a los 10 mas cercanos
     * @param page página solicitada
     * @param perPage cantidad de elementos por página
     * @return un HashMap con una clave que posee información sobre la página y otra clave con el resultado obtenido
     * @throws Exception
     */
    HashMap<Object, Object> getAverageDistanceToCloseAccidents(int page, int perPage) throws Exception;

}