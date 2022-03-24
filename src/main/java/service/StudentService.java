package main.java.service;

import main.java.dao.CrudRepository;
import main.java.model.Course;
import main.java.model.Student;

import java.util.List;

public interface StudentService extends CrudRepository<Student, Integer> {

    void updateStudent(Student student);

    void addStudentOnCourses(Student student, List<Course> courses);

    List<Student> findAllSignedOnCourse(Integer courseId);

    List<Student> findByCourseName(String courseName);

    void assignToCourse(Integer studentId, Integer courseId);

    void deleteFromCourse(Integer studentId, Integer courseId);

    void saveAll(List<Student> students);

}
