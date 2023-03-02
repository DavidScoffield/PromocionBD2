package ar.edu.unlp.info.bd2.promocionbd2.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalAccidentsInStreetRepresentation {

    private String street;
    private int totalAccidents;

    public TotalAccidentsInStreetRepresentation(String street, int totalAccidents) {
        this.street = street;
        this.totalAccidents = totalAccidents;
    }
    
}
