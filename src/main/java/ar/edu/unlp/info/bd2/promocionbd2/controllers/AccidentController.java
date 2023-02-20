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

    @GetMapping(value = "accidents/between-dates", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAccidentsBetweenDates(
            @RequestParam(value = "start") String start,
            @RequestParam(value = "end") String end,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "perPage", defaultValue = "10") Integer perPage
    ) throws Exception {     
        return ResponseEntity.status(HttpStatus.OK).body(accidentService.getAccidentsBetweenDates(start, end, page, perPage));
    }
    
    @GetMapping(value = "accidents/near", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAccidentsNearLocation(
            @RequestParam(value = "longitude") Double longitude,
            @RequestParam(value = "latitude") Double latitude,
            @RequestParam(value = "radius") Double radius
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(accidentService.getAccidentsNearLocation(longitude, latitude, radius));
    }

    @GetMapping(value = "accidents/nearLocation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAccidentsNearLocationWithElasticsearch(
            @RequestParam(value = "longitude") Double longitude,
            @RequestParam(value = "latitude") Double latitude,
            @RequestParam(value = "radius") Double radius
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(accidentService.getAccidentsNearLocationWithElasticsearch(longitude, latitude, radius));
    }

    @GetMapping(value = "accidents/average-distance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAverageDistance() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(accidentService.getAverageDistance());
    }

    @GetMapping(value = "accidents/getMostDangerousPoints", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMostDangerousPoints(
            @RequestParam(value = "radius") Double radius,
            @RequestParam(value = "amount", required = false, defaultValue = "5") Integer amount
    ) throws Exception {
        Collection<NearAccidentsSeverityRepresentation> dangerousPoints = accidentService.getMostDangerousPoints(radius, amount);

        return ResponseEntity.status(HttpStatus.OK).body(dangerousPoints);
    }

    @GetMapping(value = "accidents/dangerous-streets", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getFiveStreetsWithMostAccidents() throws Exception {
        List<String> streets = accidentService.getFiveStreetsWithMostAccidents();
        return ResponseEntity.status(HttpStatus.OK).body(streets);
    }

    @GetMapping(value = "accidents/near/average-distance")
    public ResponseEntity getAverageDistanceToCloseAccidents(
        @RequestParam(value = "page", defaultValue = "1") Integer page,
        @RequestParam(value = "perPage", defaultValue = "10") Integer perPage
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(accidentService.getAverageDistanceToCloseAccidents(page, perPage));
    }

    @GetMapping(value = "accidents/getCommonConditions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCommonAccidentConditions() throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(accidentService.getCommonAccidentConditions());
    }

}
