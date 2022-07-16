package ar.edu.unlp.info.bd2.promocionbd2.controllers;

import ar.edu.unlp.info.bd2.promocionbd2.entity.Accident;
import ar.edu.unlp.info.bd2.promocionbd2.services.AccidentServiceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


@RestController
public class AccidentController {

    @Autowired
    private AccidentServiceImplementation accidentService;

    @GetMapping(value = "upload-csv")
    public ResponseEntity uploadCsvToDataBase(@RequestParam String url) {
       try {
            accidentService.uploadCSV(url);
            return ResponseEntity.status(HttpStatus.OK).body("Uploaded the file successfully: " + url);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not upload the file: " + url + "!");
        }
    }

    @GetMapping(value = "accidents/between-dates", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAccidentsBetweenDates(
            @RequestParam(value = "start") String start,
            @RequestParam(value = "end") String end) {

        try {
            List<Accident> accidents = accidentService.getAccidentsBetweenDates(start, end);
            return ResponseEntity.status(HttpStatus.OK).body(accidents);
        } catch (ParseException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Dates could not be parsed. Make sure it follows the pattern yyyy/mm/dd");
        }
    }


    @GetMapping(value = "accidents/near", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAccidentsNearLocation(
            @RequestParam(value = "longitude") Double longitude,
            @RequestParam(value = "latitude") Double latitude,
            @RequestParam(value = "radius") Double radius) {
        try {
            List<Accident> accidents = accidentService.getAccidentsNearLocation(longitude, latitude, radius);
            return ResponseEntity.status(HttpStatus.OK).body(accidents);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
