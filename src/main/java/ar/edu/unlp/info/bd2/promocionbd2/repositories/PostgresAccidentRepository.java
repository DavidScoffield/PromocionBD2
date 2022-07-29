package ar.edu.unlp.info.bd2.promocionbd2.repositories;

import ar.edu.unlp.info.bd2.promocionbd2.model.Accident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface PostgresAccidentRepository extends JpaRepository<Accident,String> {

    List<Accident> findAllByStartTimeBetween(Date start, Date end);

    @Query("SELECT AVG(a.distance) FROM Accident a")
    Double getAverageDistance();

    @Query("SELECT a.street FROM Accident a " +
            "GROUP BY a.street " +
            "ORDER BY COUNT(a.id) DESC")
    Page<String> getFiveStreetsWithMostAccidents(Pageable pageable);

    @Query("SELECT a.weatherCondition FROM Accident a " +
            "GROUP BY a.weatherCondition " +
            "ORDER BY COUNT(a.weatherCondition) DESC")
    Page<String> getCommonAccidentWeatherCondition(Pageable pageable);

    @Query("SELECT EXTRACT (hour FROM a.startTime) FROM Accident a " +
            "GROUP BY EXTRACT (hour FROM a.startTime) " +
            "ORDER BY COUNT(EXTRACT (hour FROM a.startTime)) DESC")
    Page<Integer> getCommonAccidentStartHour(Pageable pageable);

}
