package ar.edu.unlp.info.bd2.promocionbd2.mongoRepositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import ar.edu.unlp.info.bd2.promocionbd2.model.Accident;

public interface MongoAccidentRepository extends MongoRepository<Accident, String>, CustomMongoAccidentRepository {

    Page<Accident> findAllByLocationNear(Pageable pageable, Point point, Distance distance);

    Page<Accident> findAllBy(Pageable pageable);

}
