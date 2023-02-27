package ar.edu.unlp.info.bd2.promocionbd2.controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlp.info.bd2.promocionbd2.dto.NearAccidentsSeverityRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.services.AccidentServiceImplementation;

@RestController
public class AccidentController {

    @Autowired
    private AccidentServiceImplementation accidentService;

    @ExceptionHandler
    public ResponseEntity handle(Exception e) {
        HashMap<String, String> error = new HashMap<>();
        
        try { 
            throw e.getCause();
        } catch (NumberFormatException e1) {
            error.put("error", "Bad parameter type");
        } catch (Exception e1) {
            error.put("error", e.getMessage());
        } catch (Throwable e1) {}

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * 
     * @param start la fecha de comienzo de intervalo
     * @param end la fecha de fin de intervalo
     * @param page la pagina solicitada
     * @param perPage la cantidad de elementos por pagina. (Por defecto son 10)
     * @return una respuesta HTTP con su estado y un cuerpo con una lista de los accidentes
     * que ocurrieron entre las fechas especificadas.
     * @throws Exception
     */
    @GetMapping(value = "accidents/between-dates", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAccidentsBetweenDates(
            @RequestParam(value = "start") String start,
            @RequestParam(value = "end") String end,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "perPage", defaultValue = "10") Integer perPage
    ) throws Exception {     
        return ResponseEntity.status(HttpStatus.OK).body(accidentService.getAccidentsBetweenDates(start, end, page, perPage));
    }

    /**
     * [Version Mongo] 
     * 
     * @param longitude la longitud de la ubicacion del accidente. (Entre -180 y 180)
     * @param latitude la latitud de la ubicacion del accidente. (Entre 0 y 90)
     * @param radius el radio de la circunferencia que rodea al accidente. Medido en km.
     * @return una respuesta HTTP con su estado y un cuerpo con una lista de los accidentes
     * que ocurrieron dentro del area especificada.
     * @throws Exception
     */
    @GetMapping(value = "accidents/near", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAccidentsNearLocation(
            @RequestParam(value = "longitude") Double longitude,
            @RequestParam(value = "latitude") Double latitude,
            @RequestParam(value = "radius") Double radius
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(accidentService.getAccidentsNearLocation(longitude, latitude, radius));
    }

    /**
     * [Version Elastic] 
     * 
     * @param longitude la longitud de la ubicacion del accidente. (Entre -180 y 180)
     * @param latitude la latitud de la ubicacion del accidente. (Entre 0 y 90)
     * @param radius el radio de la circunferencia que rodea al accidente. Medido en km.
     * @return una respuesta HTTP con su estado y un cuerpo con una lista de los accidentes
     * que ocurrieron dentro del area especificada.
     * @throws Exception
     */
    @GetMapping(value = "accidents/nearLocation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAccidentsNearLocationWithElasticsearch(
            @RequestParam(value = "longitude") Double longitude,
            @RequestParam(value = "latitude") Double latitude,
            @RequestParam(value = "radius") Double radius
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(accidentService.getAccidentsNearLocationWithElasticsearch(longitude, latitude, radius));
    }


    /**
     * @return una respuesta HTTP con su estado y un cuerpo con la distancia promedio (en metros)
     * entre la ubicacion de inicio y fin del accidente.
     * @throws Exception
     */
    @GetMapping(value = "accidents/average-distance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAverageDistance() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(accidentService.getAverageDistance());
    }

    /**
     * @param radius el radio de la circunferencia
     * @param amount la cantidad de puntos a obtener
     * @return una respuesta HTTP con su estado y un cuerpo con una lista de los puntos mas
     * peligrosos dentro de un area especificada.
     * @throws Exception
     */
    @GetMapping(value = "accidents/getMostDangerousPoints", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMostDangerousPoints(
            @RequestParam(value = "radius") Double radius,
            @RequestParam(value = "amount", required = false, defaultValue = "5") Integer amount
    ) throws Exception {
        Collection<NearAccidentsSeverityRepresentation> dangerousPoints = accidentService.getMostDangerousPoints(radius, amount);

        return ResponseEntity.status(HttpStatus.OK).body(dangerousPoints);
    }


    /**
     * @return una respuesta HTTP con su estado y un cuerpo con una las 5 calles con mas accidentes.
     * @throws Exception
     */
    @GetMapping(value = "accidents/dangerous-streets", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getFiveStreetsWithMostAccidents() throws Exception {
        List<String> streets = accidentService.getFiveStreetsWithMostAccidents();
        return ResponseEntity.status(HttpStatus.OK).body(streets);
    }

    /**
     * [Version Mongo]
     * 
     * @param page la pagina solicitada
     * @param perPage la cantidad de elementos por pagina. (Por defecto son 10)
     * @return una respuesta HTTP con su estado y un cuerpo con una lista de las distancias promedio 
     * entre cada accidente y los 10 mas cercanos.
     * @throws Exception
     */
    @GetMapping(value = "accidents/near/average-distance")
    public ResponseEntity getAverageDistanceToCloseAccidents(
        @RequestParam(value = "page", defaultValue = "1") Integer page,
        @RequestParam(value = "perPage", defaultValue = "10") Integer perPage
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(accidentService.getAverageDistanceToCloseAccidents(page, perPage));
    }


    /**
     * [Version Elastic]
     * 
     * @param page la pagina solicitada
     * @param perPage la cantidad de elementos por pagina. (Por defecto son 10)
     * @return una respuesta HTTP con su estado y un cuerpo con una lista de las distancias promedio 
     * entre cada accidente y los 10 mas cercanos.
     * @throws Exception
     */
    @GetMapping(value = "accidents/near/average-distance2")
    public ResponseEntity getAverageDistanceToCloseAccidents2(
        @RequestParam(value = "page", defaultValue = "1") Integer page,
        @RequestParam(value = "perPage", defaultValue = "10") Integer perPage
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(accidentService.getAverageDistanceToCloseAccidents2(page, perPage));
    }


    /**
     * 
     * @return una respuesta HTTP con su estado y un cuerpo con una lista de 
     * las condiciones mas comunes de los accidentes.
     * @throws Exception
     */
    @GetMapping(value = "accidents/getCommonConditions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCommonAccidentConditions() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(accidentService.getCommonAccidentConditions());
    }

}
