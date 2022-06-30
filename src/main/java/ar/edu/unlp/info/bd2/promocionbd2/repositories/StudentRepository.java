package ar.edu.unlp.info.bd2.promocionbd2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unlp.info.bd2.promocionbd2.entity.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    public boolean existsByEmail(String email);

    public List<Student> findByEmail(String email);

    public void deleteByEmail(String email);
}
