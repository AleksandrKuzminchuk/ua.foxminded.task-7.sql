package ua.foxminded.task7.sql.service;

import ua.foxminded.task7.sql.dao.CrudRepository;
import ua.foxminded.task7.sql.model.Course;
import ua.foxminded.task7.sql.model.Student;

import java.util.List;

public interface StudentService extends CrudRepositoryService<Student, Integer> {

    void updateStudent(Student student);

    void addStudentOnCourses(Student student, List<Course> courses);

    List<Student> findAllSignedOnCourse(Integer courseId);

    List<Student> findByCourseName(String courseName);

    void assignToCourse(Integer studentId, Integer courseId);

    void deleteFromCourse(Integer studentId, Integer courseId);

    void saveAll(List<Student> students);

}
