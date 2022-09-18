package ar.edu.unlp.info.bd2.promocionbd2.elasticsearchRepositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import ar.edu.unlp.info.bd2.promocionbd2.model.Accident;

public interface ElasticsearchAccidentRepository extends ElasticsearchRepository<Accident, String> {
    
}