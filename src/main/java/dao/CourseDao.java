package main.java.dao;

import main.java.model.Course;
import main.java.model.Student;

import java.util.List;

public interface CourseDao extends CrudRepository<Course, Integer> {

    void saveAll(List<Course> groups);

    List<Course> findByStudentId(Integer studentId);

    void addStudentsAndCourses(Student students, Course course);
}
