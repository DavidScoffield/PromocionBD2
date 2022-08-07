package ar.edu.unlp.info.bd2.promocionbd2.mongoRepositories;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;

import ar.edu.unlp.info.bd2.promocionbd2.dto.NearAccidentRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.model.Accident;

public class CustomMongoAccidentRepositoryImpl implements CustomMongoAccidentRepository{

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

    public List<NearAccidentRepresentation> getAverageDistanceToNearAccidents(Stream<Accident> accidentStream) {

        Iterator<Accident> iterator = accidentStream.iterator();

        List<NearAccidentRepresentation> accidents = new ArrayList<>();

        /*TODO Change to while(iterator.hasNext())*/
        for (int i = 0; i < 100; i++) {
            Accident accident = iterator.next();
            NearAccidentRepresentation averageDistance = getAverageDistanceToAccident(accident);

            accidents.add(averageDistance);
        }

        return accidents;
    }
}
