package main.java.Application;

import com.github.javafaker.Faker;
import main.java.config.PropertiesManager;
import main.java.exceptions.NoDBPropertiesException;
import main.java.model.Courses;
import main.java.model.Student;
import main.java.service.CourseService;
import main.java.service.GroupService;
import main.java.service.StudentService;
import main.java.util.ConnectionUtils;
import main.java.util.QueryExecutor;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Main {

    private static final String PROPERTIES_FILE = "src/main/resources/db.properties";
    private static final String URL = "db.url";
    private static final String USER = "db.user";
    private static final String PASSWORD = "db.password";

    public static void main(String[] args) {
        PropertiesManager propertiesManager = new PropertiesManager(PROPERTIES_FILE);

        String url = propertiesManager.getProperty(URL);
        String user = propertiesManager.getProperty(USER);
        String password = propertiesManager.getProperty(PASSWORD);

        ConnectionUtils connectionUtils = new ConnectionUtils(url, user, password);
        QueryExecutor queryExecutor = new QueryExecutor(connectionUtils);

        queryExecutor.createTables();

        Faker faker = new Faker();
        Random random = new Random();

        GeneratorData generatorData = new GeneratorData(faker, random);

        GroupService  groupService = new GroupService(connectionUtils);
        groupService.saveAll(generatorData.generateGroup(10));

        CourseService courseService = new CourseService(connectionUtils);
        courseService.saveAll(generatorData.generateCourses(20));

        StudentService studentService = new StudentService(connectionUtils);
        studentService.saveAll(generatorData.generateStudents(200));

//        List<Student> allStudents = studentService.findAll();
//        if (allStudents == null || allStudents.isEmpty()){
//            throw new NoDBPropertiesException("Shit happened! Check 'studentDao.saveAll' method");
//        }

//        Optional<Student> student = studentService.findById(14);

//        studentService.deleteById(12);

//                studentService.findById(12);

//        courseService.findById(10);

    }

}
