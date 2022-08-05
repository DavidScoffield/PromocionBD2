package ar.edu.unlp.info.bd2.promocionbd2.services;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ar.edu.unlp.info.bd2.promocionbd2.dto.NearAccidentRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.dto.SummarizedAccidentRepresentation;
import ar.edu.unlp.info.bd2.promocionbd2.model.Accident;
import ar.edu.unlp.info.bd2.promocionbd2.mongoRepositories.MongoAccidentRepository;
import ar.edu.unlp.info.bd2.promocionbd2.repositories.PostgresAccidentRepository;
import me.tongfei.progressbar.ProgressBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.*;
import org.springframework.stereotype.Service;

import ar.edu.unlp.info.bd2.promocionbd2.beans.CsvBean;
import ar.edu.unlp.info.bd2.promocionbd2.utils.CsvHelper;

@Service
public class AccidentServiceImplementation implements AccidentService {

    @Autowired
    private PostgresAccidentRepository postgresAccidentRepository;

    @Autowired
    private MongoAccidentRepository mongoAccidentRepository;

    public void uploadCSV(String path) throws IOException {
        Path csvPath = Paths.get(path);
        List<CsvBean> dataList = CsvHelper.beanBuilder(csvPath, Accident.class);

        for (CsvBean accident: ProgressBar.wrap(dataList,"Importing")) {
            postgresAccidentRepository.save((Accident) accident);
        }

        // TODO: Save data in mongo
    }

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
    public List<Map.Entry<Point, Integer>> getMostDangerousPoints(Double radius, Integer amount) {
        PriorityQueue<Map.Entry<Point, Integer>> maxHeap = new PriorityQueue<>((a, b) -> { return a.getValue() - b.getValue(); } );

        for (Accident accident : mongoAccidentRepository.findAll()) {
            Point location = accident.getLocation();
            try {
                int numberOfAccidents = mongoAccidentRepository.findAllByLocationNear(accident.getLocation(), new Distance(radius, Metrics.KILOMETERS)).getContent().size();
                if (maxHeap.size() == amount && numberOfAccidents > maxHeap.peek().getValue()) {
                    maxHeap.poll(); 
                }
                if (maxHeap.size() < amount)  {
                    maxHeap.add(new AbstractMap.SimpleEntry(location, numberOfAccidents));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<Map.Entry<Point, Integer>> result = new ArrayList<>();
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

    public List<NearAccidentRepresentation> getAverageDistanceToCloseAccidents() {

        Stream<Accident> accidentStream = mongoAccidentRepository.findAllBy();

        return mongoAccidentRepository.getAverageDistanceToNearAccidents(accidentStream);
    }

    public HashMap<String, Object> getCommonAccidentConditions() {
        Pageable pageable = PageRequest.of(0, 1);
        String weatherCondition = postgresAccidentRepository.getCommonAccidentWeatherCondition(pageable).getContent().get(0);
        int startHour = postgresAccidentRepository.getCommonAccidentStartHour(pageable).getContent().get(0);

        HashMap<String, Object> result = new HashMap<>();
        result.put("commonAccidentWeatherCondition", weatherCondition);
        result.put("commonAccidentStartHour", startHour);

        return result;
    }

}
