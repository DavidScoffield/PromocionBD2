package ar.edu.unlp.info.bd2.promocionbd2.dto;

import org.springframework.data.geo.Point;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SummarizedAccidentRepresentation {

    private String id;
    private Point location;
    private Double averageDistance;

    public SummarizedAccidentRepresentation(String id, Point location, Double averageDistance) {
        this.id = id;
        this.location = location;
        this.averageDistance = averageDistance;
    }
}