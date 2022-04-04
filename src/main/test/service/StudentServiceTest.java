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
import java.util.Optional;

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

        when(studentDaoMock.findAllSignedOnCourse(1)).thenReturn(Collections.singletonList(getExpectedStudent()));

        List<Student> course = testingStudentService.findAllSignedOnCourse(1);

        assertNotNull(course);
        assertFalse(course.isEmpty());
        assertEquals(1, course.size());
        assertEquals(getExpectedStudent(), course.get(0));
        verify(studentDaoMock, atMostOnce()).findAllSignedOnCourse(1);
    }

    @Test
    void shouldThrowNoDBPropertiesExceptionIfCanNotFindAllStudentsSinedOnCourse() {

    }

    @Test
    void addStudentOnCourses() {
        Course courseOne = new Course(1, "AnyName", "AnyDescription");
        Course courseTwo = new Course(2, "AnyName2", "AnyDescription2");
        List<Course> courses = new LinkedList<>();
        courses.add(courseOne);
        courses.add(courseTwo);

        assertNotNull(getExpectedStudent());
        assertNotNull(courses);
        assertFalse(courses.isEmpty());

        doNothing().when(studentDaoMock).addStudentAndCourse(getExpectedStudent(), courseOne);
        doNothing().when(studentDaoMock).addStudentAndCourse(getExpectedStudent(), courseTwo);

        testingStudentService.addStudentOnCourses(getExpectedStudent(), courses);

        verify(studentDaoMock, atMostOnce()).addStudentAndCourse(getExpectedStudent(), courseOne);
        verify(studentDaoMock, atMostOnce()).addStudentAndCourse(getExpectedStudent(), courseTwo);
    }

    @Test
    void shouldThrowNoDBPropertiesExceptionIfCanNotAddStudentOnCourses(){

    }

    @Test
    void shouldDeleteStudentByIdAndCheckNotNullAndEquals(){

        doNothing().when(studentDaoMock).deleteById(getExpectedStudentId());

        testingStudentService.deleteById(getExpectedStudentId());

        verify(studentDaoMock, atMostOnce()).deleteById(getExpectedStudentId());
    }

    @Test
    void shouldThrowNoDBPropertiesExceptionIfCanNotDeleteStudentById(){

    }

    @Test
    void shouldSaveStudentAndCheckNotNullAndEquals(){

        when(studentDaoMock.save(getExpectedStudent())).thenReturn(Optional.of(getExpectedStudent()));

        testingStudentService.save(getExpectedStudent());

        verify(studentDaoMock, atMostOnce()).save(getExpectedStudent());
    }

    @Test
    void shouldThrowNoDBPropertiesExceptionIfCanNotSaveStudent(){

    }

    @Test
    void shouldFindStudentByIdAndCheckNotNullAndEquals(){

        when(studentDaoMock.findById(getExpectedStudentId())).thenReturn(Optional.of(getExpectedStudent()));

        testingStudentService.findById(getExpectedStudentId());

        verify(studentDaoMock, atMostOnce()).findById(getExpectedStudentId());
    }

    @Test
    void shouldThrowNoDBPropertiesExceptionIfCanNotFindStudentById(){

    }

    @Test
    void shouldFindAllStudents(){
        when(studentDaoMock.findAll()).thenReturn((getExpectedStudents()));

        List<Student> allFoundStudents = testingStudentService.findAll();

        assertNotNull(allFoundStudents);
        assertFalse(allFoundStudents.isEmpty());
        assertEquals(allFoundStudents, getExpectedStudents());
        assertEquals(2, allFoundStudents.size());

        verify(studentDaoMock, atMostOnce()).findAll();
    }

    @Test
    void shouldThrowNoDBPropertiesExceptionIfCanNotFindAllStudents(){

    }

    @Test
    void shouldSaveAllStudentsAndCheckNotNullAndEmpty(){

        doNothing().when(studentDaoMock).saveAll(getExpectedStudents());

        testingStudentService.saveAll(getExpectedStudents());

        verify(studentDaoMock, times(1)).saveAll(getExpectedStudents());
    }

    @Test
    void shouldThrowNoDBPropertiesExceptionIfCanNotSaveAllStudents(){

    }

    @Test
    void shouldFindByCourseNameAndCheckCourseNameNotNullAndEmpty(){
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
    void shouldThrowNoDBPropertiesExceptionIfCanNotFindStudentsByCourseName(){

    }

    @Test
    void shouldAssignToCourseAndCheckNotNull(){

        doNothing().when(studentDaoMock).assignToCourse(getExpectedStudentId(),getExpectedCourseId());

        testingStudentService.assignToCourse(getExpectedStudentId(),getExpectedCourseId());

        verify(studentDaoMock, times(1)).assignToCourse(getExpectedStudentId(),getExpectedCourseId());
    }

    @Test
    void shouldThrowNoDBPropertiesExceptionIfCanNotAssignStudentOnCourse(){

    }

    @Test
    void shouldDeleteFromCourseAndCheckNotNull(){

        doNothing().when(studentDaoMock).deleteFromCourse(getExpectedStudentId(), getExpectedCourseId());

        testingStudentService.deleteFromCourse(getExpectedStudentId(),getExpectedCourseId());

        verify(studentDaoMock, times(1)).deleteFromCourse(getExpectedStudentId(),getExpectedCourseId());
    }

    @Test
    void shouldThrowNoDBPropertiesExceptionIfCanNotDeleteFromCourse(){

    }

    @Test
    void shouldFindAllSignedOnCourse(){

        when(studentDaoMock.findAllSignedOnCourse(getExpectedCourseId())).thenReturn(getExpectedStudents());

        List<Student> allStudentsSignedOnCourse = testingStudentService.findAllSignedOnCourse(getExpectedCourseId());

        assertNotNull(allStudentsSignedOnCourse);
        assertFalse(allStudentsSignedOnCourse.isEmpty());
        assertEquals(2, allStudentsSignedOnCourse.size());
        assertEquals(allStudentsSignedOnCourse, getExpectedStudents());

        verify(studentDaoMock, atMostOnce()).findAllSignedOnCourse(getExpectedCourseId());
    }

    @Test
    void shouldThrowNoDBPropertiesExceptionIfCanNotFindAllStudentsSignedOnCourse(){

    }

    @Test
    void shouldReturnCountStudents(){
        long preparedCountStudents = 10;

        when(studentDaoMock.count()).thenReturn(preparedCountStudents);

        long foundCountStudents = testingStudentService.count();

        assertEquals(foundCountStudents, 10);

        verify(studentDaoMock, atMostOnce()).count();
    }

    @Test
    void shouldThrowNoDBPropertiesExceptionIfCanNotReturnCountStudents(){

    }

    @Test
    void shouldDeleteStudent(){

        doNothing().when(studentDaoMock).delete(getExpectedStudent());

        testingStudentService.delete(getExpectedStudent());

        verify(studentDaoMock, times(1)).delete(getExpectedStudent());
    }

    @Test
    void shouldThrowNoDBPropertiesExceptionIfCanNotDeleteStudent(){

    }

    @Test
    void shouldUpdateStudentAndCheckNotNull(){

        doNothing().when(studentDaoMock).updateStudent(getExpectedStudent());

        testingStudentService.updateStudent(getExpectedStudent());

        verify(studentDaoMock, times(1)).updateStudent(getExpectedStudent());
    }

    @Test
    void shouldThrowNoDBPropertiesExceptionIfCanNotUpdateStudent(){

    }

    @Test
    void shouldDeleteAll(){
        doNothing().when(studentDaoMock).deleteAll();

        testingStudentService.deleteAll();

        verify(studentDaoMock, times(1)).deleteAll();
    }

    @Test
    void shouldThrowNoDBPropertiesExceptionIfCanNotDeleteAllStudents(){

    }

    private List<Student> getExpectedStudents(){
        Student student1 = new Student("any1", "any2");
        Student student2 = new Student("any3", "any4");

        List<Student> preparedStudents = new LinkedList<>();
        preparedStudents.add(student1);
        preparedStudents.add(student2);

        return preparedStudents;
    }

    private Integer getExpectedStudentId(){
        Integer studentId = 1;
        return studentId;
    }

    private Integer getExpectedCourseId(){
        Integer courseId = 2;
        return courseId;
    }

    private Student getExpectedStudent(){
        return new Student("any", "any");
    }

}