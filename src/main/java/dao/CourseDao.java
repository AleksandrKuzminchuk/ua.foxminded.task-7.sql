package dao;

import model.Course;

import java.util.List;

public interface CourseDao extends CrudRepository<Course, Integer> {

    void updateCourse(Course course);

    void saveAll(List<Course> courses);

    List<Course> findByStudentId(Integer studentId);
}
