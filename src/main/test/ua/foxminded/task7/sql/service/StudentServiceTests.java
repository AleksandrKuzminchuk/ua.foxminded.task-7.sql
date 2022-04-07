package ua.foxminded.task7.sql.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.foxminded.task7.sql.dao.StudentDao;
import ua.foxminded.task7.sql.model.Course;
import ua.foxminded.task7.sql.model.Student;
import ua.foxminded.task7.sql.service.impl.StudentServiceImpl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTests {
    private static StudentDao studentDaoMock;
    private static StudentService testingStudentService;//не надо было "исправлять" -- было как раз правильно (мы тестируем реализацию интерфейса)

    @BeforeAll
    static void beforeAll() {
        studentDaoMock = Mockito.mock(StudentDao.class);
        testingStudentService = new StudentServiceImpl(studentDaoMock);
    }

    @BeforeEach
    void reset() {
        Mockito.reset(studentDaoMock);
    }

    @Test
    void expectedOneStudentSignedOnCourse() {
        Student expectedStudent = getExpectedStudent();
        when(studentDaoMock.findAllSignedOnCourse(1)).thenReturn(Collections.singletonList(expectedStudent));

        List<Student> course = testingStudentService.findAllSignedOnCourse(1);

        assertNotNull(course);
        assertFalse(course.isEmpty());
        assertEquals(1, course.size());
        assertEquals(expectedStudent, course.get(0));
        verify(studentDaoMock, atMostOnce()).findAllSignedOnCourse(1);
    }

    @Test
    void expectedIllegalArgumentExceptionWhenFindAllSignedOnCourseIsNull() {
        assertThrows(IllegalArgumentException.class, () -> testingStudentService.findAllSignedOnCourse(null));
    }

    @Test
    void expectedEmptyListIfNoStudentsFound() {
        when(studentDaoMock.findAll()).thenReturn(Collections.singletonList(null));

        List<Student> result = testingStudentService.findAll();

        assertNotNull(result);
    }

    @Test
    void addStudentOnCourses() {
        Course courseOne = new Course(1, "AnyName", "AnyDescription");
        Course courseTwo = new Course(2, "AnyName2", "AnyDescription2");
        List<Course> courses = new LinkedList<>();
        courses.add(courseOne);
        courses.add(courseTwo);

        doNothing().when(studentDaoMock).addStudentOnCourse(getExpectedStudent(), courseOne);
        doNothing().when(studentDaoMock).addStudentOnCourse(getExpectedStudent(), courseTwo);

        testingStudentService.addStudentOnCourses(getExpectedStudent(), courses);

        verify(studentDaoMock, atMostOnce()).addStudentOnCourse(getExpectedStudent(), courseOne);
        verify(studentDaoMock, atMostOnce()).addStudentOnCourse(getExpectedStudent(), courseTwo);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionIfAddStudentOnCoursesHasNullArg() {
        assertThrows(IllegalArgumentException.class, () -> testingStudentService.addStudentOnCourses(null, null));
    }

    @Test
    void shouldDeleteStudentById() {
        doNothing().when(studentDaoMock).deleteById(1);

        testingStudentService.deleteById(1);

        verify(studentDaoMock, atMostOnce()).deleteById(1);
    }

    @Test
    void shouldThrowExceptionNotNullInMethodDeleteById() {
        assertThrows(IllegalArgumentException.class, () -> testingStudentService.deleteById(null));
    }

    @Test
    void shouldSaveStudent() {
        Student expectedStudent = getExpectedStudent();
        when(studentDaoMock.save(getExpectedStudent())).thenReturn(getStudentOptional());

        Student result = testingStudentService.save(getExpectedStudent());

        assertNotNull(result);
        assertEquals(expectedStudent, result);

        verify(studentDaoMock, atMostOnce()).save(getExpectedStudent());
    }

    @Test
    void shouldThrowExceptionNotNullInMethodSave() {
        assertThrows(IllegalArgumentException.class, () -> testingStudentService.save(null));
    }


    @Test
    void shouldFindStudentById() throws Exception {
        Student expectedStudent = getExpectedStudent();
        when(studentDaoMock.findById(1)).thenReturn(getStudentOptional());

        Student result = testingStudentService.findById(1);

        assertNotNull(result);
        assertEquals(expectedStudent, result);

        verify(studentDaoMock, atMostOnce()).findById(1);
    }

    @Test
    void shouldThrowExceptionNotNullInMethodFindById() {
        assertThrows(IllegalArgumentException.class, () -> testingStudentService.findById(null));
    }

    @Test
    void shouldFindAllStudents() {
        when(studentDaoMock.findAll()).thenReturn((getExpectedStudents()));

        List<Student> allFoundStudents = testingStudentService.findAll();

        assertNotNull(allFoundStudents);
        assertFalse(allFoundStudents.isEmpty());
        assertEquals(getExpectedStudents(), allFoundStudents);
        assertEquals(2, allFoundStudents.size());

        verify(studentDaoMock, atMostOnce()).findAll();
    }

    @Test
    void shouldSaveAllStudents() {
        List<Student> expectedStudents = getExpectedStudents();
        doNothing().when(studentDaoMock).saveAll(expectedStudents);

        testingStudentService.saveAll(expectedStudents);

        verify(studentDaoMock, times(1)).saveAll(expectedStudents);
    }

    @Test
    void shouldThrowExceptionNotNullInMethodSaveAll() {
        assertThrows(IllegalArgumentException.class, () -> testingStudentService.saveAll(null));
    }

    @Test
    void shouldFindByCourseName() {
        Course courseName = new Course(1, "AnyName", "AnyDescription");
        when(studentDaoMock.findByCourseName(courseName.getCourseName())).thenReturn(getExpectedStudents());

        List<Student> foundStudentsByCourseName = testingStudentService.findByCourseName(courseName.getCourseName());

        assertNotNull(foundStudentsByCourseName);
        assertFalse(foundStudentsByCourseName.isEmpty());
        assertEquals(2, foundStudentsByCourseName.size());
        assertEquals(foundStudentsByCourseName, getExpectedStudents());
        verify(studentDaoMock, atMostOnce()).findByCourseName(courseName.getCourseName());
    }

    @Test
    void shouldThrowExceptionNotNullInMethodFindByCourseName() {
        assertThrows(IllegalArgumentException.class, () -> testingStudentService.findByCourseName(null));
    }

    @Test
    void shouldAssignToCourse() {
        doNothing().when(studentDaoMock).assignToCourse(1, 1);

        testingStudentService.assignToCourse(1, 1);

        verify(studentDaoMock, times(1)).assignToCourse(1, 1);
    }

    @Test
    void shouldThrowExceptionNotNullInMethodAssignToCourse() {
        assertThrows(IllegalArgumentException.class, () -> testingStudentService.assignToCourse(null, null));
    }

    @Test
    void shouldDeleteFromCourse() {
        doNothing().when(studentDaoMock).deleteFromCourse(1, 1);

        testingStudentService.deleteFromCourse(1, 1);

        verify(studentDaoMock, times(1)).deleteFromCourse(1, 1);
    }

    @Test
    void shouldThrowExceptionNotNullInMethodDeleteFromCourse() {
        assertThrows(IllegalArgumentException.class, () -> testingStudentService.deleteFromCourse(null, null));
    }

    @Test
    void shouldReturnCountStudents() {
        long preparedCountStudents = 10;
        when(studentDaoMock.count()).thenReturn(preparedCountStudents);
        long foundCountStudents = testingStudentService.count();

        assertEquals(10, foundCountStudents);

        verify(studentDaoMock, atMostOnce()).count();
    }

    @Test
    void shouldDeleteStudent() {
        doNothing().when(studentDaoMock).delete(getExpectedStudent());

        testingStudentService.delete(getExpectedStudent());

        verify(studentDaoMock, times(1)).delete(getExpectedStudent());
    }

    @Test
    void shouldThrowExceptionNotNullInMethodDelete() {
        assertThrows(IllegalArgumentException.class, () -> testingStudentService.delete(null));
    }

    @Test
    void shouldUpdateStudent() {
        doNothing().when(studentDaoMock).updateStudent(getExpectedStudent());

        testingStudentService.updateStudent(getExpectedStudent());

        verify(studentDaoMock, times(1)).updateStudent(getExpectedStudent());
    }

    @Test
    void shouldThrowExceptionNotNullInMethodUpdateStudent() {
        assertThrows(IllegalArgumentException.class, () -> testingStudentService.updateStudent(null));
    }

    @Test
    void shouldDeleteAll() {
        doNothing().when(studentDaoMock).deleteAll();

        testingStudentService.deleteAll();

        verify(studentDaoMock, times(1)).deleteAll();
    }

    private List<Student> getExpectedStudents() {
        Student student1 = new Student("any1", "any2");
        Student student2 = new Student("any3", "any4");

        List<Student> preparedStudents = new LinkedList<>();
        preparedStudents.add(student1);
        preparedStudents.add(student2);

        return preparedStudents;
    }

    private Student getExpectedStudent() {
        return new Student("any", "any");
    }

    private Optional<Student> getStudentOptional(){
        return Optional.of(new Student("any", "any"));
    }

}
