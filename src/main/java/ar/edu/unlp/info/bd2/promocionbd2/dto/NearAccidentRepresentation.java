package ar.edu.unlp.info.bd2.promocionbd2.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.geo.Point;

@Getter
@Setter
public class NearAccidentRepresentation {
    private String ID;
    private Double averageDistance;
    private Double calculatedDistance;
}
