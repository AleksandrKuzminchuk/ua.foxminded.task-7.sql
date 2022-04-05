package ua.foxminded.task7.sql.util;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.foxminded.task7.sql.model.Course;
import ua.foxminded.task7.sql.model.Group;
import ua.foxminded.task7.sql.model.Student;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorDataTest {

    private GeneratorData generatorData;
    private Random random = new Random();
    private Faker faker = new Faker();

    @BeforeEach
    void BeforeEach() {
        this.generatorData = new GeneratorData(faker, random);
    }

    @Test
    void shouldGenerateGroups() {
        List<Group> groups = generatorData.generateGroups(10);
        assertEquals(10, groups.size());
        assertNotNull(groups);
        assertFalse(groups.isEmpty());
    }

    @Test
    void shouldThrowExceptionNotNullInMethodGenerateGroups(){
        assertThrows(IllegalArgumentException.class, () -> {generatorData.generateGroups(null);});
    }

    @Test
    void shouldGenerateStudents() {
        List<Group> groups = generatorData.generateGroups(10);
        List<Student> students = generatorData.generateStudents(200, groups);

        assertNotNull(students);
        assertFalse(students.isEmpty());
        assertEquals(200, students.size());
    }

    @Test
    void shouldThrowExceptionNotNullInMethodGenerateStudents(){
        assertThrows(IllegalArgumentException.class, () -> {generatorData.generateStudents(null, null);});
    }

    @Test
    void shouldGenerateCourses() {
        List<Course> courses = generatorData.generateCourses(20);

        assertNotNull(courses);
        assertFalse(courses.isEmpty());
        assertEquals(20, courses.size());
    }

    @Test
    void shouldThrowExceptionNotNullInMethodGenerateCourses(){
        assertThrows(IllegalArgumentException.class, () -> {generatorData.generateCourses(null);});
    }
}