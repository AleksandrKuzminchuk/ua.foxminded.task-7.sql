package dao;

import model.Course;
import model.Student;

import java.util.List;

public interface CourseDao extends CrudRepository<Course, Integer> {

    void saveAll(List<Course> groups);

    List<Course> findByStudentId(Integer studentId);

    void addStudentAndCourse(Student students, Course course);
}
