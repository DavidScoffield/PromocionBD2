package ar.edu.unlp.info.bd2.promocionbd2.mongoRepositories;

import ar.edu.unlp.info.bd2.promocionbd2.model.Accident;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.stream.Stream;

public interface MongoAccidentRepository extends MongoRepository<Accident, String>, CustomMongoAccidentRepository{

    GeoResults<Accident> findAllByLocationNear(Point point, Distance distance);

    Stream<Accident> findAllBy();

    List<Accident> findAll();
}
