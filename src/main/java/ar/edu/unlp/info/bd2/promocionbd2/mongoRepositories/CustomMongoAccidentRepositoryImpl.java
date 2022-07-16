package ar.edu.unlp.info.bd2.promocionbd2.mongoRepositories;

import ar.edu.unlp.info.bd2.promocionbd2.dto.NearAccidentRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.dto.SummarizedAccidentRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.model.Accident;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.NearQuery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class CustomMongoAccidentRepositoryImpl implements CustomMongoAccidentRepository{

    @Autowired
    private MongoTemplate mongoTemplate;

    /*TODO modify so it is faster. It works but it's very slow*/

    private List<NearAccidentRepresentation> getNearAccidents(Accident accident) {

        NearQuery nearQuery = NearQuery.near(accident.getLocation()).spherical(true).limit(11);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.geoNear(nearQuery,"calculatedDistance"));
        AggregationResults<NearAccidentRepresentation> results = mongoTemplate.aggregate(aggregation,"accident", NearAccidentRepresentation.class);

        return results.getMappedResults();
    }

    public List<SummarizedAccidentRepresentation> getAverageDistanceToNearAccidents(Stream<Accident> accidentStream) {

        //  DistinctIterable<Accident> iterable = mongoTemplate.getCollection("accident").distinct("ID", Accident.class);
        //  MongoCursor<Accident> mongoCursor = iterable.iterator();
        Iterator<Accident> iterator = accidentStream.iterator();

        List<SummarizedAccidentRepresentation> accidents = new ArrayList<>();

        /*TODO Change to while(iterator.hasNext())*/
        for (int i = 0; i < 50; i++) {
            Accident accident = iterator.next();
            List<NearAccidentRepresentation> nearAccidents = getNearAccidents(accident);

            Double partialSum = 0.0;
            for (NearAccidentRepresentation nearAccident: nearAccidents) {
                    partialSum += nearAccident.getCalculatedDistance();
            }
            Double averageDistance = partialSum / 10;

            SummarizedAccidentRepresentation accidentRep = new SummarizedAccidentRepresentation(
                    accident.getId(),
                    accident.getLocation(),
                    averageDistance);

            accidents.add(accidentRep);
        }

        return accidents;
    }
}
