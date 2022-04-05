package ua.foxminded.task7.sql.util;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import ua.foxminded.task7.sql.exceptions.ExceptionsHandlingConstants;
import ua.foxminded.task7.sql.model.Course;
import ua.foxminded.task7.sql.model.Group;
import ua.foxminded.task7.sql.model.Student;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;
import java.util.stream.IntStream;

public class GeneratorData {

    private static final String GROUP_NAME_SEPARATOR = "-";

    private Faker faker;
    private Random random;

    public GeneratorData(Faker generatorData, Random generatorNumber) {
        this.faker = generatorData;
        this.random = generatorNumber;
    }

    public List<Group> generateGroups(Integer number) {
        requiredNonNull(number);
        List<Group> groups = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            groups.add(createGroup());
        }
        return groups;
    }

    public List<Student> generateStudents(Integer number, List<Group> groups) {
        requiredNonNull(number);
        requiredNonNull(groups);
        List<Student> students = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            students.add(createStudent(groups.size()));
        }
        return students;
    }

    private Student createStudent(Integer integer) {
        Name name = faker.name();
        return new Student(null, getRandomNumberInRange(1, integer), name.firstName(), name.lastName());
    }

    public List<Course> generateCourses(Integer number) {
        requiredNonNull(number);
        List<Course> courses = new ArrayList<>(number);

        for (int i = 0; i < number; i++) {
            courses.add(createCourse());
        }
        return courses;
    }

    private Group createGroup() {
        return new Group(null, generateRandomGroupName());
    }

    private String generateRandomGroupName() {
        return RandomStringUtils.randomAlphabetic(2) + GROUP_NAME_SEPARATOR + RandomStringUtils.randomNumeric(2);
    }

    private Course createCourse() {
        String name = faker.educator().course();
        return new Course(null, name, "Course of " + name);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private int getRandomNumberInRange(Integer min, Integer max) {
        return random.ints(min, max + 1)
                .limit(1).findFirst().getAsInt();
    }

    private void requiredNonNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException(ExceptionsHandlingConstants.ARGUMENT_IS_NULL);
        }
    }

}
