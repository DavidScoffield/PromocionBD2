package ar.edu.unlp.info.bd2.promocionbd2.controllers;

import ar.edu.unlp.info.bd2.promocionbd2.messages.ResponseMessage;
import ar.edu.unlp.info.bd2.promocionbd2.services.AccidentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccidentController {
    @Autowired
    private AccidentService accidentService;

    @RequestMapping(value = "upload-csv", method = RequestMethod.GET)
    public ResponseEntity<ResponseMessage> createStudent(@RequestParam String url) {

        String message = "";

        try {
            accidentService.uploadCSV(url);

            message = "Uploaded the file successfully: " + url;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + url + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

}
