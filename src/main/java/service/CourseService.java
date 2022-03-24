package service;

import dao.CrudRepository;
import model.Course;

import java.util.List;

public interface CourseService extends CrudRepository<Course, Integer> {

    void updateCourse(Course course);

    void saveAll(List<Course> groups);

    List<Course> findByStudentId(Integer studentId);
}
