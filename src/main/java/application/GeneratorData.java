package main.java.application;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import main.java.model.Course;
import main.java.model.Group;
import main.java.model.Student;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;
import java.util.stream.IntStream;

public class GeneratorData {

    private static final String GROUP_NAME_SEPARATOR = "-";

    private final Faker faker;
    private final Random random;

    public GeneratorData(Faker generatorData, Random generatorNumber) {
        this.faker = generatorData;
        this.random = generatorNumber;
    }

    public List<Group> generateGroup(Integer number) {
        List<Group> groups = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            groups.add(createGroup());
        }
        return groups;
    }

    public List<Student> generateStudents(Integer number, List<Group> groups) {
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
        List<Course> courses = new ArrayList<>(number);

        for (int i = 0; i < number; i++) {
            courses.add(createCourse());
        }
        return courses;
    }

    public void assignStudentsToGroups(List<Group> groups, List<Student> students, Integer minStudentNumber, Integer maxStudentNumber) {
        LinkedList<Student> studentsAssign = new LinkedList<>(students);
        Collections.shuffle(studentsAssign);
        groups.forEach(group -> {
            IntStream.rangeClosed(1, getRandomNumberInRange(minStudentNumber, maxStudentNumber))
                    .forEach(i -> {
                        if (!studentsAssign.isEmpty()) {
                            studentsAssign.removeFirst().setGroupId(group.getGroupId());
                        }
                    });
        });
    }

    public void assignCoursesToStudents(List<Course> courses, List<Student> students, Integer minCoursesNumber, Integer maxCoursesNumber) {
        students.forEach(student -> {
            IntStream.range(1, getRandomNumberInRange(minCoursesNumber, maxCoursesNumber));
            assignStudentToRandomCourse(courses, student);
        });
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

    private void assignStudentToRandomCourse(List<Course> courses, Student student) {
        Course course = courses.get(getRandomNumberInRange(0, courses.size() - 1));
        List<Student> students = course.getStudents();
        Optional.ofNullable(students).ifPresent(list -> list.add(student));
    }

}
