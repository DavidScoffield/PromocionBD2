package ar.edu.unlp.info.bd2.promocionbd2.elasticsearchRepositories;

import java.util.List;

import ar.edu.unlp.info.bd2.promocionbd2.dto.NearAccidentRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.model.Accident;

public interface CustomElasticsearchAccidentRepository {

    NearAccidentRepresentation getAverageDistanceToAccident(Accident accident);

    NearAccidentRepresentation[] getAverageDistanceToNearAccidents(List<Accident> accidents);

}