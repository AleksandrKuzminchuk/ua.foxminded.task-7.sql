package main.java.service;

import main.java.dao.CourseDao;
import main.java.dao.CrudRepository;
import main.java.model.Course;

import java.util.List;

public interface CourseService extends CrudRepository<Course, Integer> {

    void updateCourse(Course course);

    void saveAll(List<Course> groups);

    List<Course> findByStudentId(Integer studentId);
}
