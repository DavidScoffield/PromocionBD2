package ar.edu.unlp.info.bd2.promocionbd2.elasticsearchRepositories;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ar.edu.unlp.info.bd2.promocionbd2.dto.NearAccidentRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.model.Accident;

import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;


public class CustomElasticsearchAccidentRepositoryImpl implements CustomElasticsearchAccidentRepository {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    public NearAccidentRepresentation getAverageDistanceToAccident(Accident accident) {
        GeoPoint geopoint = accident.geoPoint();

        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .filter(QueryBuilders.geoDistanceQuery("geopoint").point(geopoint).distance("5km"))
                .mustNot(QueryBuilders.termQuery("id", accident.getId()));

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withQuery(queryBuilder)
        .withSort(SortBuilders.geoDistanceSort("geopoint", geopoint).order(SortOrder.ASC).unit(DistanceUnit.KILOMETERS))
        .withPageable(PageRequest.of(0, 10))
        .build();
        
        SearchHits<Accident> hits = elasticsearchOperations.search(searchQuery, Accident.class, IndexCoordinates.of("accident"));
        
        double totalDistance = 0.0;
        int totalAccidents = 0;
        for (SearchHit<Accident> hit : hits) {
            totalDistance += (double) hit.getSortValues().get(0);
            totalAccidents += 1;
        }        

        NearAccidentRepresentation result = new NearAccidentRepresentation();
        result.setID(accident.getId());
        result.setAverageDistance(totalDistance / totalAccidents);

        return result;
    }

    @Override
    public NearAccidentRepresentation[] getAverageDistanceToNearAccidents(List<Accident> accidents) {
        int totalResults = accidents.size();
        NearAccidentRepresentation result[] = new NearAccidentRepresentation[totalResults];
        ExecutorService executorService = Executors.newFixedThreadPool(Math.min(totalResults, 100));

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
        while (!executorService.isTerminated()) {
        }

        return result;
    }

}
