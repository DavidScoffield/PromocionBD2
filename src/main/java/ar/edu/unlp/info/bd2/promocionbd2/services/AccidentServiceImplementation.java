package ar.edu.unlp.info.bd2.promocionbd2.services;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import ar.edu.unlp.info.bd2.promocionbd2.dto.NearAccidentsSeverityRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.model.Accident;
import ar.edu.unlp.info.bd2.promocionbd2.mongoRepositories.MongoAccidentRepository;
import ar.edu.unlp.info.bd2.promocionbd2.repositories.PostgresAccidentRepository;
import ar.edu.unlp.info.bd2.promocionbd2.elasticsearchRepositories.ElasticsearchAccidentRepository;

@Service
public class AccidentServiceImplementation implements AccidentService {

    @Autowired
    private PostgresAccidentRepository postgresAccidentRepository;

    @Autowired
    private MongoAccidentRepository mongoAccidentRepository;

    @Autowired
    private ElasticsearchAccidentRepository elasticsearchAccidentRepository;

    private HashMap<String, Integer> getPaginationInfo(Page<Object> page) {
        HashMap<String, Integer> paginationInfo = new HashMap<>();

        paginationInfo.put("totalPages", page.getTotalPages());
        paginationInfo.put("currentPage", page.getPageable().getPageNumber() + 1);
        paginationInfo.put("perPage", page.getPageable().getPageSize());
        paginationInfo.put("totalElements", (int) page.getTotalElements());
        paginationInfo.put("totalElementsInCurrentPage", page.getNumberOfElements());

        return paginationInfo;
    }

    private void checkCoordinates(Double latitude, Double longitude) throws Exception {
        if ((!(longitude >= -180.0 && longitude <= 180.0)) || (!(latitude >= -90 && latitude <= 90))) {
            throw new Exception("Longitude or latitude value out of bounds");
        }
    }

    private void checkRadius(Double radius) throws Exception {
        if (radius <= 0) {
            throw new Exception("Radius has to be greater than 0");
        }
    }

    private void checkPaginationParams(Integer page, Integer perPage) throws Exception {
        if (page < 0 || perPage < 1) {
            throw new Exception("Invalid pagination params");
        }
    }

    @Override
    public HashMap<Object, Object> getAccidentsBetweenDates(String startDate, String endDate, int page, int perPage) throws Exception {
        Date start, end;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            formatter.setLenient(false);
            start = formatter.parse(startDate);
            end = formatter.parse(endDate);
        } catch (Exception e) {
            throw new Exception("Date must follow the pattern dd-mm-yyyy");
        }
        page--;
        checkPaginationParams(page, perPage);

        Page<Accident> accidentsPage = postgresAccidentRepository.findAllByStartTimeBetween(start, end, PageRequest.of(page, perPage));
        HashMap<Object, Object> result = new HashMap<>();

        result.put("paginationInfo", getPaginationInfo(accidentsPage.map((Accident a) -> {return null;})));
        result.put("content", accidentsPage.getContent());

        return result;
    }

    @Override
    public List<Accident> getAccidentsNearLocation(Double longitude, Double latitude, Double radius) throws Exception {
        checkCoordinates(latitude, longitude);
        checkRadius(radius);

        Point point = new Point(longitude, latitude);
        Distance distance = new Distance(radius, Metrics.KILOMETERS);

        List<Accident> accidents = mongoAccidentRepository.findAllByLocationNear(point, distance)
                .getContent()
                .stream()
                .map(GeoResult::getContent)
                .collect(Collectors.toList());

        return accidents;
    }

    @Override
    public List<Accident> getAccidentsNearLocationWithElasticsearch(Double longitude, Double latitude, Double radius) throws Exception {
        checkCoordinates(latitude, longitude);
        checkRadius(radius);

        Point point = new Point(longitude, latitude);
        Distance distance = new Distance(radius, Metrics.KILOMETERS);
        List<Accident> accidents = elasticsearchAccidentRepository.findAllByLocationNear(point, distance);
        return accidents;
    }

    @Override
    public HashMap<String, Object> getAverageDistance() throws Exception {
        HashMap<String, Object> result = new HashMap<>();

        try {
            // Retorna la distancia en metros
            Double distance = postgresAccidentRepository.getAverageDistance() * 1609.34;
            result.put("averageDistance", distance);
        } catch (NullPointerException e) {
            result.put("averageDistance", "No distance found");
        }

        return result;
    }

    @Override
    public Collection<NearAccidentsSeverityRepresentation> getMostDangerousPoints(Double radius, Integer amount) throws Exception {
        if (amount < 1) {
            throw new Exception("Amount has to be greater than 0");
        }

        checkRadius(radius);

        return mongoAccidentRepository.getMostDangerousPoints(radius, amount);
    }

    @Override
    public List<String> getFiveStreetsWithMostAccidents() throws Exception {
        Pageable pageable = PageRequest.of(0, 5);

        return postgresAccidentRepository.getFiveStreetsWithMostAccidents(pageable).getContent();
    }

    @Override
    public HashMap<Object, Object> getAverageDistanceToCloseAccidents(int page, int perPage) throws Exception {
        page--;
        checkPaginationParams(page, perPage);

        Page<Accident> accidentsPage = mongoAccidentRepository.findAllBy(PageRequest.of(page, perPage));
        HashMap<Object, Object> result = new HashMap<>();

        result.put("paginationInfo", getPaginationInfo(accidentsPage.map((Accident a) -> {return null;})));
        result.put("content", mongoAccidentRepository.getAverageDistanceToNearAccidents(accidentsPage.getContent()));
        
        return result;
    }

    @Override
    public HashMap<String, Object> getCommonAccidentConditions() throws Exception {
        Pageable pageable = PageRequest.of(0, 1);
        int totalResults = 5;
        Object results[] = new Object[totalResults];
        Thread threads[] = new Thread[totalResults];
        HashMap<String, Object> hashMap = new HashMap<>();

        for (int i = 0; i < totalResults; i++) {
            final int j = i; 
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Object result = null;
                    switch(j) {
                        case 0:
                            result = postgresAccidentRepository.getCommonAccidentWeatherCondition(pageable);
                            break;
                        case 1:
                            result = postgresAccidentRepository.getCommonAccidentWindDirection(pageable);
                            break;
                        case 2:
                            result = postgresAccidentRepository.getCommonAccidentVisibility(pageable);
                            break;
                        case 3:
                            result = postgresAccidentRepository.getCommonAccidentHumidity(pageable);
                            break;
                        case 4:
                            result = postgresAccidentRepository.getCommonAccidentStartHour(pageable);
                            break;
                    }
                    results[j] = ((Page<Object>) result).getContent().get(0);
                }
            });

            threads[i] = thread;
            thread.start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (Exception e) {}

        hashMap.put("commonAccidentWeatherCondition", results[0]);
        hashMap.put("commonAccidentWindDirection", results[1]);
        hashMap.put("commonAccidentVisibility", results[2]);
        hashMap.put("commonAccidentHumidity", results[3]);
        hashMap.put("commonAccidentStartHour", results[4]);

        return hashMap;
    }

}