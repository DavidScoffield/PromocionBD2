package ar.edu.unlp.info.bd2.promocionbd2.dto;

import org.springframework.data.geo.Point;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NearAccidentsSeverityRepresentation {
    private Point point;
    private int totalSeverity;
    private int totalAccidentsInLocation;
    private int totalNearAccidents;
}