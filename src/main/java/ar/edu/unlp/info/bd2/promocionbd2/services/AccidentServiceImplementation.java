package ar.edu.unlp.info.bd2.promocionbd2.services;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.unlp.info.bd2.promocionbd2.entity.Accident;
import ar.edu.unlp.info.bd2.promocionbd2.mongoRepositories.MongoAccidentRepository;
import ar.edu.unlp.info.bd2.promocionbd2.repositories.PostgresAccidentRepository;
import me.tongfei.progressbar.ProgressBar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.stereotype.Service;

import ar.edu.unlp.info.bd2.promocionbd2.beans.CsvBean;
import ar.edu.unlp.info.bd2.promocionbd2.utils.CsvHelper;

@Service
public class AccidentServiceImplementation implements AccidentService{

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


}
