package ar.edu.unlp.info.bd2.promocionbd2.repositories;

import ar.edu.unlp.info.bd2.promocionbd2.entity.Accident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PostgresAccidentRepository extends JpaRepository<Accident,String> {

    List<Accident> findAllByStartTimeBetween(Date start, Date end);
}
