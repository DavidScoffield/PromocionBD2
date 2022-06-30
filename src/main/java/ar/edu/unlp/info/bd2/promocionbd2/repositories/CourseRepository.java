package ar.edu.unlp.info.bd2.promocionbd2.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unlp.info.bd2.promocionbd2.document.Course;

import java.util.List;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {

    public List<Course> findCourseByEmail(String email);

    public void deleteByEmail(String email);
}
