package ua.foxminded.task7.sql.dao;


import ua.foxminded.task7.sql.model.Course;
import ua.foxminded.task7.sql.model.Student;

import java.util.List;

public interface StudentDao extends CrudRepository<Student, Integer> {

    void saveAll(List<Student> students);

    List<Student> findByCourseName(String courseName);

    void assignToCourse(Integer studentId, Integer courseId);

    void deleteFromCourse(Integer studentId, Integer courseId);

    List<Student> findAllSignedOnCourse(Integer courseId);

    void updateStudent(Student student);

    void addStudentOnCourses(Student student, List<Course> courses);

    void addStudentOnCourse(Student students, Course course);
}
