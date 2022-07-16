package ar.edu.unlp.info.bd2.promocionbd2.dto;

import ar.edu.unlp.info.bd2.promocionbd2.entity.Accident;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccidentRepresentation {
    private String id;
    private String source;
    private String tcm;

    public static AccidentRepresentation of(Accident accident) {
        AccidentRepresentation dto = new AccidentRepresentation();

        dto.setId(accident.getId());
        dto.setSource(accident.getSource());
        dto.setTcm(accident.getTmc());

        return dto;
    }
}
