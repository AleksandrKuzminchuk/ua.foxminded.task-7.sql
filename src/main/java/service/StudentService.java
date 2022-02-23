package main.java.service;

import main.java.dao.CourseDao;
import main.java.model.Course;
import main.java.model.Student;

import java.util.List;
import java.util.Random;

public class StudentService {

    private final CourseDao courseDao;
    private final Random random;

    public StudentService(CourseDao courseDao, Random random) {
        this.random = random;
        this.courseDao = courseDao;
    }

    public void addStudentsOnCourses(List<Student> allStudents, List<Course> allCourses) {
        for (int i = 0; i < allStudents.size() + allCourses.size(); i++) {
            Student student = allStudents.get(random.nextInt(allStudents.size()));
            Course course = allCourses.get(random.nextInt(allCourses.size()));
            courseDao.addStudentsAndCourses(student, course);
        }
    }
}
