package main.java.dao;

import main.java.model.Courses;
import main.java.model.Groups;
import main.java.model.Student;

import java.util.List;

public interface CourseDao extends CrudRepository<Courses, Integer>{

    void saveAll(List<Courses> groups);

    List<Courses> findByStudentId(Integer studentId);

    void addStudentsAndCourses(Student students, Courses courses);
}
