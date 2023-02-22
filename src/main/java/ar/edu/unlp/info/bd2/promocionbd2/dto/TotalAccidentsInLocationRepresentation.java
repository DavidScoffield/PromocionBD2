package ar.edu.unlp.info.bd2.promocionbd2.dto;

import org.springframework.data.geo.Point;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalAccidentsInLocationRepresentation {
    private Point point;
    private int totalAccidentsInLocation;
}