package ar.edu.unlp.info.bd2.promocionbd2.repositories;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.edu.unlp.info.bd2.promocionbd2.model.Accident;

public interface PostgresAccidentRepository extends JpaRepository<Accident,String> {

    Page<Accident> findAllByStartTimeBetween(Date start, Date end, Pageable pageable);

    @Query("SELECT AVG(a.distance) FROM Accident a")
    Double getAverageDistance();
    
    @Query("SELECT a.street, COUNT(a.id) FROM Accident a " +
           "GROUP BY a.street " +
           "ORDER BY COUNT(a.id) DESC")
    Page<Object[]> getFiveStreetsWithMostAccidents(Pageable pageable);

    @Query("SELECT a.weatherCondition FROM Accident a " +
           "GROUP BY a.weatherCondition " +
           "ORDER BY COUNT(a.weatherCondition) DESC")
    Page<String> getCommonAccidentWeatherCondition(Pageable pageable);

    @Query("SELECT a.windDirection FROM Accident a " +
           "GROUP BY a.windDirection " +
           "ORDER BY COUNT(a.windDirection) DESC")
    Page<String> getCommonAccidentWindDirection(Pageable pageable);

    @Query("SELECT a.visibility FROM Accident a " +
           "GROUP BY a.visibility " +
           "ORDER BY COUNT(a.visibility) DESC")
    Page<Double> getCommonAccidentVisibility(Pageable pageable);

    @Query("SELECT a.humidity FROM Accident a " +
           "GROUP BY a.humidity " +
           "ORDER BY COUNT(a.humidity) DESC")
    Page<Double> getCommonAccidentHumidity(Pageable pageable);

    @Query("SELECT EXTRACT (hour FROM a.startTime) FROM Accident a " +
           "GROUP BY EXTRACT (hour FROM a.startTime) " +
           "ORDER BY COUNT(EXTRACT (hour FROM a.startTime)) DESC")
    Page<Integer> getCommonAccidentStartHour(Pageable pageable);

}
