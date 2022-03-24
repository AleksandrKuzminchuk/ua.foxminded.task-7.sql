package main.java.dao;

import main.java.model.Course;
import main.java.model.Student;

import java.util.List;

public interface StudentDao extends CrudRepository<Student, Integer> {

    void saveAll(List<Student> students);

    List<Student> findByCourseName(String courseName);

    void assignToCourse(Integer studentId, Integer courseId);

    void deleteFromCourse(Integer studentId, Integer courseId);

    List<Student> findAllSignedOnCourse(Integer courseId);

    void updateStudent(Student student);

    void addStudentOnCourses(Student student, List<Course> courses);

    void addStudentAndCourse(Student students, Course course);
}
