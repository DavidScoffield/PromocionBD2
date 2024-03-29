package ar.edu.unlp.info.bd2.promocionbd2.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import ar.edu.unlp.info.bd2.promocionbd2.dto.NearAccidentsSeverityRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.dto.TotalAccidentsInStreetRepresentation;
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

    private Pageable checkPaginationParams(Integer page, Integer perPage) throws Exception {
        page--;
        if (page < 0 || perPage < 1) {
            throw new Exception("Invalid pagination params");
        }
        return PageRequest.of(page, perPage);
    }

    private HashMap<String, Object> getResults(Page<Accident> accidentsPage) {
        List<Accident> accidentList = new ArrayList<>(accidentsPage.getContent());

        accidentList.sort(Comparator.comparing(Accident::getId));

        HashMap<String, Object> result = new HashMap<>();
        result.put("paginationInfo", getPaginationInfo(accidentsPage.map((Accident a) -> {
            return null;
        })));
        result.put("content", accidentList);

        return result;
    }

    @Override
    public HashMap<String, Object> getAccidentsBetweenDates(String startDate, String endDate, int page, int perPage)
            throws Exception {
        Date start, end;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            formatter.setLenient(false);
            start = formatter.parse(startDate);
            end = formatter.parse(endDate);
        } catch (Exception e) {
            throw new Exception("Date must follow the pattern DD-MM-YYYY");
        }

        Page<Accident> accidentsPage = postgresAccidentRepository.findAllByStartTimeBetween(start, end,
                                                                        checkPaginationParams(page, perPage));
        
        return getResults(accidentsPage);
    }

    @Override
    public HashMap<String, Object> getAccidentsNearLocation(int page, int perPage, Double longitude, Double latitude, Double radius) throws Exception {
        checkCoordinates(latitude, longitude);
        checkRadius(radius);

        Point point = new Point(longitude, latitude);
        Distance distance = new Distance(radius, Metrics.KILOMETERS);
        Page<Accident> accidentsPage = mongoAccidentRepository.findAllByLocationNear(checkPaginationParams(page, perPage), point, distance);
        
        return getResults(accidentsPage);
    }

    @Override
    public HashMap<String, Object> getAccidentsNearLocationWithElasticsearch(int page, int perPage, Double longitude, Double latitude, Double radius)
            throws Exception {
        checkCoordinates(latitude, longitude);
        checkRadius(radius);

        Point point = new Point(longitude, latitude);
        Distance distance = new Distance(radius, Metrics.KILOMETERS);
        Page<Accident> accidentsPage = elasticsearchAccidentRepository.findAllByGeopointNear(checkPaginationParams(page, perPage), point, distance);
        
        return getResults(accidentsPage);
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
    public Collection<NearAccidentsSeverityRepresentation> getMostDangerousPoints(Double radius, Integer amount)
            throws Exception {
        if (amount < 1) {
            throw new Exception("Amount has to be greater than 0");
        }

        checkRadius(radius);

        return mongoAccidentRepository.getMostDangerousPoints(radius, amount);
    }

    @Override
    public List<TotalAccidentsInStreetRepresentation> getFiveStreetsWithMostAccidents() throws Exception {
        List<Object[]> result = postgresAccidentRepository.getFiveStreetsWithMostAccidents(PageRequest.of(0, 5)).getContent();

        List<TotalAccidentsInStreetRepresentation> list = new ArrayList<>();

        for (Object[] row : result) {
            String street = (String) row[0];
            int totalAccidents = ((Number) row[1]).intValue();
            list.add(new TotalAccidentsInStreetRepresentation(street, totalAccidents));
        }

        return list;
    }

    @Override
    public HashMap<String, Object> getAverageDistanceToCloseAccidents(int page, int perPage) throws Exception {
        Page<Accident> accidentsPage = mongoAccidentRepository.findAllBy(checkPaginationParams(page, perPage));
        
        HashMap<String, Object> result = getResults(accidentsPage);
        result.put("content", mongoAccidentRepository.getAverageDistanceToNearAccidents(accidentsPage.getContent()));

        return result;
    }

    @Override
    public HashMap<String, Object> getAverageDistanceToCloseAccidents2(int page, int perPage) throws Exception {
        Page<Accident> accidentsPage = elasticsearchAccidentRepository.findAllBy(checkPaginationParams(page, perPage));

        HashMap<String, Object> result = getResults(accidentsPage);
        result.put("content", elasticsearchAccidentRepository.getAverageDistanceToNearAccidents(accidentsPage.getContent()));

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
                    switch (j) {
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
        } catch (Exception e) {
        }

        hashMap.put("commonAccidentWeatherCondition", results[0]);
        hashMap.put("commonAccidentWindDirection", results[1]);
        hashMap.put("commonAccidentVisibility", results[2]);
        hashMap.put("commonAccidentHumidity", results[3]);
        hashMap.put("commonAccidentStartHour", results[4]);

        return hashMap;
    }

}