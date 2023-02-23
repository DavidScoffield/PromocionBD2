package ar.edu.unlp.info.bd2.promocionbd2.elasticsearchRepositories;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.unlp.info.bd2.promocionbd2.model.Accident;

public interface ElasticsearchAccidentRepository extends ElasticsearchRepository<Accident, String>, CustomElasticsearchAccidentRepository {

    List<Accident> findAllByLocationNear(Point point, Distance distance);

    Page<Accident> findAllBy(Pageable pageable);
    
}