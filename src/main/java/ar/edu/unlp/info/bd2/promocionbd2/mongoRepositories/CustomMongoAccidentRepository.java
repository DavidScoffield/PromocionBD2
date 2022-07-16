package ar.edu.unlp.info.bd2.promocionbd2.mongoRepositories;

import ar.edu.unlp.info.bd2.promocionbd2.dto.SummarizedAccidentRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.model.Accident;

import java.util.List;
import java.util.stream.Stream;

public interface CustomMongoAccidentRepository {

    public List<SummarizedAccidentRepresentation> getAverageDistanceToNearAccidents(Stream<Accident> accidentStream);
}
