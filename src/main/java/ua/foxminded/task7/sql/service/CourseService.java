package ua.foxminded.task7.sql.service;

import ua.foxminded.task7.sql.dao.CrudRepository;
import ua.foxminded.task7.sql.model.Course;

import java.util.List;

public interface CourseService extends CrudRepositoryService<Course, Integer> {

    void updateCourse(Course course);

    void saveAll(List<Course> groups);

    List<Course> findByStudentId(Integer studentId);
}
