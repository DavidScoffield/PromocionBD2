package ar.edu.unlp.info.bd2.promocionbd2.mongoRepositories;

import java.util.List;
import java.util.Collection;

import ar.edu.unlp.info.bd2.promocionbd2.dto.NearAccidentRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.dto.NearAccidentsSeverityRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.model.Accident;

public interface CustomMongoAccidentRepository {
    NearAccidentRepresentation[] getAverageDistanceToNearAccidents(List<Accident> accidents);

    Collection<NearAccidentsSeverityRepresentation> getMostDangerousPoints(Double radius, Integer amount);
}