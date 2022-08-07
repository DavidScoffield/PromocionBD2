package ar.edu.unlp.info.bd2.promocionbd2.mongoRepositories;

import java.util.List;
import java.util.stream.Stream;

import ar.edu.unlp.info.bd2.promocionbd2.dto.NearAccidentRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.model.Accident;

public interface CustomMongoAccidentRepository {
    public List<NearAccidentRepresentation> getAverageDistanceToNearAccidents(Stream<Accident> accidentStream);
}