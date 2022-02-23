package main.java.application;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import main.java.model.Courses;
import main.java.model.Groups;
import main.java.model.Student;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.IntStream;

public class GeneratorData {

    private static final String GROUP_NAME_SEPARATOR = "-";

    private final Faker faker;
    private final Random generatorNumber;

    public GeneratorData(Faker generatorData, Random generatorNumber) {
        this.faker = generatorData;
        this.generatorNumber = generatorNumber;
    }

    private static final Logger logger = Logger.getLogger(GeneratorData.class);

    public List<Groups> generateGroup(Integer number) {
        List<Groups> groups = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            groups.add(createGroup());
        }
        return groups;
    }

    public List<Student> generateStudents(Integer number, List<Groups> groups) {
        List<Student> students = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            students.add(createStudent(groups.size()));
        }
        return students;
    }

    private Student createStudent(Integer integer) {
        Name name = faker.name();
        return new Student(null, getRandomNumberInRange(1, integer),name.firstName(), name.lastName());
    }

    public List<Courses> generateCourses(Integer number) {
        List<Courses> courses = new ArrayList<>(number);

        for (int i = 0; i < number; i++) {
            courses.add(createCourse());
        }
        return courses;
    }

    public void assignStudentsToGroups(List<Groups> groups, List<Student> students, Integer minStudentNumber, Integer maxStudentNumber) {
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

    public void assignCoursesToStudents(List<Courses> courses, List<Student> students, Integer minCoursesNumber, Integer maxCoursesNumber) {
        students.forEach(student -> {
            IntStream.range(1, getRandomNumberInRange(minCoursesNumber, maxCoursesNumber));
            assignStudentToRandomCourse(courses, student);
        });
    }

    private Groups createGroup() {
        return new Groups(null, generateRandomGroupName());
    }

    private String generateRandomGroupName() {
        return RandomStringUtils.randomAlphabetic(2) + GROUP_NAME_SEPARATOR + RandomStringUtils.randomNumeric(2);
    }

    private Courses createCourse() {
        String name = faker.educator().course();
        return new Courses(null, name, "Course of " + name);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private int getRandomNumberInRange(Integer min, Integer max) {
        return generatorNumber.ints(min, max + 1)
                .limit(1).findFirst().getAsInt();
    }
    private void assignStudentToRandomCourse(List<Courses> courses, Student student) {
        Courses course = courses.get(getRandomNumberInRange(0, courses.size() - 1));
        List<Student> students = course.getStudents();
        Optional.ofNullable(students).ifPresent(list -> list.add(student));
    }

}
