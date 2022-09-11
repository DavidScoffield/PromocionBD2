package ar.edu.unlp.info.bd2.promocionbd2.mongoRepositories;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.geo.Point;

import ar.edu.unlp.info.bd2.promocionbd2.dto.NearAccidentRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.dto.NearAccidentsSeverityRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.dto.TotalAccidentsInLocationRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.model.Accident;

public interface CustomMongoAccidentRepository {
    public List<NearAccidentRepresentation> getAverageDistanceToNearAccidents(Stream<Accident> accidentStream);

    public NearAccidentsSeverityRepresentation getNearAccidentsSeverity(TotalAccidentsInLocationRepresentation location, double radius);

    List<TotalAccidentsInLocationRepresentation> findLocationsWithMostAccidents(int maxLocations);
}