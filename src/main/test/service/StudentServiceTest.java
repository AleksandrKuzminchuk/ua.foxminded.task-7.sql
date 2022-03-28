package service;

import dao.CourseDao;
import dao.StudentDao;
import model.Course;
import model.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service.impl.StudentServiceImpl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {
    private static CourseDao courseDaoMock;
    private static StudentDao studentDaoMock;
    private static StudentServiceImpl testingStudentService;

    @BeforeAll
    static void beforeAll() {
        courseDaoMock = Mockito.mock(CourseDao.class);
        studentDaoMock = Mockito.mock(StudentDao.class);
        testingStudentService = new StudentServiceImpl(courseDaoMock, studentDaoMock);
    }

    @Test
    void expectedOneStudentSignedOnCourse() {
        Student preparedStudent = new Student("any", "any");
        when(studentDaoMock.findAllSignedOnCourse(1)).thenReturn(Collections.singletonList(preparedStudent));

        List<Student> course = testingStudentService.findAllSignedOnCourse(1);

        assertNotNull(course);
        assertFalse(course.isEmpty());
        assertEquals(1, course.size());
        assertEquals(preparedStudent, course.get(0));
        verify(studentDaoMock, atMostOnce()).findAllSignedOnCourse(1);
    }

    @Test
    void addStudentOnCourses() {
        Student preparedStudent = new Student("any", "any");
        Course courseOne = new Course(1, "AnyName", "AnyDescript");
        Course courseTwo = new Course(2, "AnyName2", "AnyDesc2");
        List<Course> courses = new LinkedList<>();
        courses.add(courseOne);
        courses.add(courseTwo);

        doNothing().when(studentDaoMock).addStudentAndCourse(preparedStudent, courseOne);
        doNothing().when(studentDaoMock).addStudentAndCourse(preparedStudent, courseTwo);

        testingStudentService.addStudentOnCourses(preparedStudent, courses);

        verify(studentDaoMock, atMostOnce()).addStudentAndCourse(preparedStudent, courseOne);
        verify(studentDaoMock, atMostOnce()).addStudentAndCourse(preparedStudent, courseTwo);
    }

    @Test
    void updateStudent(){
        Student preparedStudent = new Student("Oleksandr", "Kuzminchuk");

    }
}