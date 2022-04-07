package ua.foxminded.task7.sql.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.foxminded.task7.sql.dao.CourseDao;
import ua.foxminded.task7.sql.exceptions.NotFoundException;
import ua.foxminded.task7.sql.model.Course;
import ua.foxminded.task7.sql.service.impl.CourseServiceImpl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {
    private static CourseDao courseDaoMock;
    private static CourseService testingCourseService;

    @BeforeAll
    static void beforeAll(){
        courseDaoMock = Mockito.mock(CourseDao.class);
        testingCourseService = new CourseServiceImpl(courseDaoMock);
    }

    @BeforeEach
    void reset(){
        Mockito.reset(courseDaoMock);
    }

    @Test
    void shouldSaveCourse(){
        Course expectedCourse = getExpectedCourse();
        when(courseDaoMock.save(getExpectedCourse())).thenReturn(getExpectedCourseOptional());

        Course result = testingCourseService.save(getExpectedCourse());

        assertNotNull(result);
        assertEquals(expectedCourse, result);

        verify(courseDaoMock, atMostOnce()).save(getExpectedCourse());
    }

    @Test
    void shouldThrowExceptionNotNullInMethodSave(){
        assertThrows(IllegalArgumentException.class, () -> {testingCourseService.save(null);});
    }

    @Test
    void shouldFindCourseById() throws Exception {
        Course expectedCourse = getExpectedCourse();
        when(courseDaoMock.findById(1)).thenReturn(getExpectedCourseOptional());

        Course result = testingCourseService.findById(1);

        assertNotNull(result);
        assertEquals(expectedCourse, result);

        verify(courseDaoMock, atMostOnce()).findById(1);
    }

    @Test
    void shouldThrowExceptionNotNullInMethodFindById(){
        assertThrows(IllegalArgumentException.class, () -> {testingCourseService.findById(null);});
    }

    @Test
    void shouldFindAllCourses(){
        when(courseDaoMock.findAll()).thenReturn(getExpectedCourses());

        List<Course> allFoundCourses = testingCourseService.findAll();

        assertNotNull(allFoundCourses);
        assertFalse(allFoundCourses.isEmpty());
        assertEquals(getExpectedCourses(), allFoundCourses);
        assertEquals(2, allFoundCourses.size());

        verify(courseDaoMock, atMostOnce()).findAll();
    }

    @Test
    void expectedEmptyListIfNoCoursesFound(){
        when(courseDaoMock.findAll()).thenReturn(Collections.singletonList(null));

        List<Course> result = testingCourseService.findAll();

        assertNotNull(result);
    }

    @Test
    void shouldReturnCountCourses(){
        long preparedCountCourses = 10;
        when(courseDaoMock.count()).thenReturn(preparedCountCourses);
        long foundCountCourses = testingCourseService.count();

        assertEquals(10, foundCountCourses);

        verify(courseDaoMock, atMostOnce()).count();
    }

    @Test
    void shouldDeleteCourseById(){
        doNothing().when(courseDaoMock).deleteById(1);

        testingCourseService.deleteById(1);

        verify(courseDaoMock, atMostOnce()).deleteById(1);
    }

    @Test
    void shouldThrowExceptionNotNullInMethodDeleteById(){
        assertThrows(IllegalArgumentException.class, () -> {testingCourseService.deleteById(null);});
    }

    @Test
    void shouldDeleteCourse(){
        doNothing().when(courseDaoMock).delete(getExpectedCourse());

        testingCourseService.delete(getExpectedCourse());

        verify(courseDaoMock, atMostOnce()).delete(getExpectedCourse());
    }

    @Test
    void shouldThrowExceptionNotNullInMethodDelete(){
        assertThrows(IllegalArgumentException.class, () -> {testingCourseService.delete(null);});
    }

    @Test
    void shouldDeleteAll(){
        doNothing().when(courseDaoMock).deleteAll();

        testingCourseService.deleteAll();

        verify(courseDaoMock, atMostOnce()).deleteAll();
    }

    @Test
    void shouldUpdateCourse(){
        doNothing().when(courseDaoMock).updateCourse(getExpectedCourse());

        testingCourseService.updateCourse(getExpectedCourse());

        verify(courseDaoMock, atMostOnce()).updateCourse(getExpectedCourse());
    }

    @Test
    void shouldThrowExceptionNotNullInMethodUpdateCourse(){
        assertThrows(IllegalArgumentException.class, () -> {testingCourseService.updateCourse(null);});
    }

    @Test
    void shouldSaveAll(){
        doNothing().when(courseDaoMock).saveAll(getExpectedCourses());

        testingCourseService.saveAll(getExpectedCourses());

        verify(courseDaoMock, atMostOnce()).saveAll(getExpectedCourses());
    }

    @Test
    void shouldThrowExceptionNotNullInMethodSaveAll(){
        assertThrows(IllegalArgumentException.class, () -> {testingCourseService.saveAll(null);});
    }

    @Test
    void shouldFindCoursesByStudentId(){
        List<Course> expectedCourses = getExpectedCourses();
        when(courseDaoMock.findByStudentId(1)).thenReturn(expectedCourses);

        List<Course> result = testingCourseService.findByStudentId(1);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(expectedCourses, result);
        assertEquals(2, result.size());

        verify(courseDaoMock, atMostOnce()).findByStudentId(1);
    }

    @Test
    void shouldThrowExceptionNotNullInMethodFindByStudentId(){
        assertThrows(IllegalArgumentException.class, () -> {testingCourseService.findByStudentId(null);});
    }

    private Course getExpectedCourse(){
        return new Course(1, "AnyName", "AnyDescription");
    }

    private Optional<Course> getExpectedCourseOptional(){
        return Optional.of(new Course(1, "AnyName", "AnyDescription"));
    }

    private List<Course> getExpectedCourses(){
        Course course1 = new Course(1, "AnyName1", "AnyDescription1");
        Course course2 = new Course(2, "AnyName2", "AnyDescription2");
        List<Course> courses = new LinkedList<>();
        courses.add(course1);
        courses.add(course2);
        return courses;
    }

}