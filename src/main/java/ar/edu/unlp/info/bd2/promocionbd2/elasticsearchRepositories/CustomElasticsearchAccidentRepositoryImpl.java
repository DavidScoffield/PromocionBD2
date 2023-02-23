package ar.edu.unlp.info.bd2.promocionbd2.elasticsearchRepositories;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import ar.edu.unlp.info.bd2.promocionbd2.dto.NearAccidentRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.model.Accident;

public class CustomElasticsearchAccidentRepositoryImpl implements CustomElasticsearchAccidentRepository {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    public NearAccidentRepresentation getAverageDistanceToAccident(Accident accident) {
        GeoPoint location = accident.geoPoint();

        QueryBuilder query = QueryBuilders.boolQuery()
                .filter(QueryBuilders.geoDistanceQuery("location").point(location).distance("10km"))
                .mustNot(QueryBuilders.termQuery("id", accident.getId()));

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(query)
                .withPageable(PageRequest.of(0, 10))
                .build();

        List<SearchHit<Accident>> accidents = elasticsearchOperations.search(searchQuery, Accident.class).get()
                .collect(Collectors.toList());

        Double averageDistance = accidents.stream()
                .mapToDouble(a -> distance(a.getContent().geoPoint(), location))
                .average()
                .orElse(0.0);

        NearAccidentRepresentation result = new NearAccidentRepresentation();
        result.setID(accident.getId());
        result.setAverageDistance(averageDistance);

        return result;
    }

    public static double distance(GeoPoint point1, GeoPoint point2) {
        // método para calcular la distancia entre dos puntos

        double lat1, lon1, lat2, lon2;
        lat1 = point1.getLat();
        lon1 = point1.getLon();
        lat2 = point2.getLat();
        lon2 = point2.getLon();

        final double R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        return distance;
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
        while (!executorService.isTerminated()) {}

        return result;
    }

}
