package ar.edu.unlp.info.bd2.promocionbd2.mongoRepositories;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;

import ar.edu.unlp.info.bd2.promocionbd2.dto.NearAccidentRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.dto.NearAccidentsSeverityRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.dto.TotalAccidentsInLocationRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.model.Accident;

public class CustomMongoAccidentRepositoryImpl implements CustomMongoAccidentRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    private NearAccidentRepresentation getAverageDistanceToAccident(Accident accident) {

        NearQuery nearQuery = NearQuery.near(accident.getLocation())
                .spherical(true)
                .limit(10)
                .query(new Query().addCriteria(Criteria.where("ID").ne(accident.getId())))
                .inKilometers();

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.geoNear(nearQuery,"calculatedDistance"),
                Aggregation.group().avg("calculatedDistance").as("averageDistance"));

        AggregationResults<NearAccidentRepresentation> results = mongoTemplate.aggregate(aggregation,"accident", NearAccidentRepresentation.class);

        NearAccidentRepresentation res = results.getMappedResults().get(0);
        res.setID(accident.getId());

        return res;
    }

    public NearAccidentRepresentation[] getAverageDistanceToNearAccidents(List<Accident> accidents) {
        int totalResults = accidents.size();
        NearAccidentRepresentation result[] = new NearAccidentRepresentation[totalResults];
        ExecutorService executorService = Executors.newFixedThreadPool(totalResults < 100 ? totalResults : 100);

        for (int i = 0; i < totalResults; i++) {
            final int j = i; 
            
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    result[j] = getAverageDistanceToAccident(accidents.get(j));
                }
            });

            executorService.execute(thread);
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {}

        return result;
    }

    public List<TotalAccidentsInLocationRepresentation> findLocationsWithMostAccidents(int maxLocations) {
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("location").count().as("totalAccidentsInLocation"),
                                                             Aggregation.sort(Sort.Direction.DESC, "totalAccidentsInLocation"),
                                                             Aggregation.project("totalAccidentsInLocation").and("_id").as("point").andExclude("_id"),
                                                             Aggregation.limit(maxLocations));

        AggregationResults<TotalAccidentsInLocationRepresentation> aggregationResults = mongoTemplate.aggregate(aggregation, "accident", TotalAccidentsInLocationRepresentation.class);
        return aggregationResults.getMappedResults();
    }

    public NearAccidentsSeverityRepresentation getNearAccidentsSeverity(TotalAccidentsInLocationRepresentation point, double radius) {

        NearQuery nearQuery = NearQuery.near(point.getPoint(), Metrics.KILOMETERS).spherical(true).maxDistance(radius);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.geoNear(nearQuery,"calculatedDistance"),
                Aggregation.group("$id").count().as("totalNearAccidents").sum("$Severity").as("totalSeverity")
        );
        AggregationResults<NearAccidentsSeverityRepresentation> aggregationResults = mongoTemplate.aggregate(aggregation, "accident", NearAccidentsSeverityRepresentation.class);
        NearAccidentsSeverityRepresentation result = aggregationResults.getMappedResults().get(0);
        result.setPoint(new Point(point.getPoint()));
        result.setTotalAccidentsInLocation(point.getTotalAccidentsInLocation());
        result.setTotalNearAccidents(result.getTotalNearAccidents() - point.getTotalAccidentsInLocation());

        return result;
    }
}
