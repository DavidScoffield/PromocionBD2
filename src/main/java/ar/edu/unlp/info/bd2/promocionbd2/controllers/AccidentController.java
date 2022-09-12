package ar.edu.unlp.info.bd2.promocionbd2.controllers;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlp.info.bd2.promocionbd2.dto.NearAccidentsSeverityRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.model.Accident;
import ar.edu.unlp.info.bd2.promocionbd2.services.AccidentServiceImplementation;

@RestController
public class AccidentController {

    @Autowired
    private AccidentServiceImplementation accidentService;

    @GetMapping(value = "accidents/between-dates", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAccidentsBetweenDates(
            @RequestParam(value = "start") String start,
            @RequestParam(value = "end") String end,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "perPage") Integer perPage) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(accidentService.getAccidentsBetweenDates(start, end, page, perPage));
        } catch (ParseException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Dates could not be parsed. Make sure it follows the pattern yyyy/mm/dd");
        }
    }


    @GetMapping(value = "accidents/near", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAccidentsNearLocation(
            @RequestParam(value = "longitude") Double longitude,
            @RequestParam(value = "latitude") Double latitude,
            @RequestParam(value = "radius") Double radius
    ) {
        try {
            List<Accident> accidents = accidentService.getAccidentsNearLocation(longitude, latitude, radius);
            return ResponseEntity.status(HttpStatus.OK).body(accidents);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(value = "accidents/average-distance", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAverageDistance() {
        return ResponseEntity.status(HttpStatus.OK).body(accidentService.getAverageDistance());
    }

    // TODO check parameters
    @GetMapping(value = "accidents/getMostDangerousPoints", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMostDangerousPoints(
            @RequestParam(value = "radius") Double radius,
            @RequestParam(value = "amount", required = false) Integer amount
    ) {
        List<NearAccidentsSeverityRepresentation> dangerousPoints = accidentService.getMostDangerousPoints(radius, amount == null ? 5 : amount);

        return ResponseEntity.status(HttpStatus.OK).body(dangerousPoints);
    }

    @GetMapping(value = "accidents/dangerous-streets", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getFiveStreetsWithMostAccidents() {
        List<String> streets = accidentService.getFiveStreetsWithMostAccidents();
        return ResponseEntity.status(HttpStatus.OK).body(streets);
    }

    @GetMapping(value = "accidents/near/average-distance")
    public ResponseEntity getAverageDistanceToCloseAccidents(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "perPage") Integer perPage
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(accidentService.getAverageDistanceToCloseAccidents(page, perPage));
    }

    @GetMapping(value = "accidents/getCommonConditions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCommonAccidentConditions() {
        return ResponseEntity.status(HttpStatus.OK).body(accidentService.getCommonAccidentConditions());
    }

}
