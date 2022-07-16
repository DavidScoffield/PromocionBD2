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
}
