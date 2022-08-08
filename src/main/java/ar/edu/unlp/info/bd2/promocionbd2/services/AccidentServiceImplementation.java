package ar.edu.unlp.info.bd2.promocionbd2.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import ar.edu.unlp.info.bd2.promocionbd2.dto.NearAccidentRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.dto.NearAccidentsSeverityRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.model.Accident;
import ar.edu.unlp.info.bd2.promocionbd2.mongoRepositories.MongoAccidentRepository;
import ar.edu.unlp.info.bd2.promocionbd2.repositories.PostgresAccidentRepository;

@Service
public class AccidentServiceImplementation implements AccidentService {

    @Autowired
    private PostgresAccidentRepository postgresAccidentRepository;

    @Autowired
    private MongoAccidentRepository mongoAccidentRepository;

    public List<Accident> getAccidentsBetweenDates(String date1, String date2) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date start = formatter.parse(date1);
        Date end = formatter.parse(date2);

        List<Accident> accidents = postgresAccidentRepository.findAllByStartTimeBetween(start, end);

        return accidents;
    }

    @Override
    public List<Accident> getAccidentsNearLocation(Double longitude, Double latitude, Double radius) throws Exception {

        if ((!(longitude >= -180.0 && longitude <= 180.0)) || (!(latitude >= -90 && latitude <= 90))) {
            throw new Exception("Longitude or latitude value out of bounds.");
        }

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
    public HashMap<String, Object> getAverageDistance() {
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

    /*TODO check parameters and performance*/
    @Override
    public List<NearAccidentsSeverityRepresentation> getMostDangerousPoints(Double radius, Integer amount) {
        PriorityQueue<NearAccidentsSeverityRepresentation> maxHeap = new PriorityQueue<>((a, b) -> { return a.getTotalSeverity() - b.getTotalSeverity(); } );

        mongoAccidentRepository.findAllBy().forEach( (Accident accident) -> {
            try {
                NearAccidentsSeverityRepresentation nearAccidents = mongoAccidentRepository.getNearAccidentsSeverity(accident, radius);
                int totalSeverity = nearAccidents.getTotalSeverity();
                if (maxHeap.size() == amount && totalSeverity > maxHeap.peek().getTotalSeverity()) {
                    maxHeap.poll(); 
                }
                if (maxHeap.size() < amount)  {
                    maxHeap.add(nearAccidents);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } );

        List<NearAccidentsSeverityRepresentation> result = new ArrayList<>();
        while(!maxHeap.isEmpty()){
            result.add(0, maxHeap.poll());
        }

        return result;
    }

    @Override
    public List<String> getFiveStreetsWithMostAccidents() {
        Pageable pageable = PageRequest.of(0,5);

        return postgresAccidentRepository.getFiveStreetsWithMostAccidents(pageable).getContent();
    }

    @Override
    public List<NearAccidentRepresentation> getAverageDistanceToCloseAccidents() {

        Stream<Accident> accidentsStream = mongoAccidentRepository.findAllBy();

        return mongoAccidentRepository.getAverageDistanceToNearAccidents(accidentsStream);
    }

    @Override
    public HashMap<String, Object> getCommonAccidentConditions() {
        Pageable pageable = PageRequest.of(0, 1);
        String weatherCondition = postgresAccidentRepository.getCommonAccidentWeatherCondition(pageable).getContent().get(0);
        String windDirection = postgresAccidentRepository.getCommonAccidentWindDirection(pageable).getContent().get(0);
        int startHour = postgresAccidentRepository.getCommonAccidentStartHour(pageable).getContent().get(0);

        HashMap<String, Object> result = new HashMap<>();
        result.put("commonAccidentWeatherCondition", weatherCondition);
        result.put("commonAccidentWindDirection", windDirection);
        result.put("commonAccidentStartHour", startHour);

        return result;
    }

}
