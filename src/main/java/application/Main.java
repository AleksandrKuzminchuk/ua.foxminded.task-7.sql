package main.java.application;

import com.github.javafaker.Faker;
import main.java.config.PropertiesManager;
import main.java.dao.CourseDao;
import main.java.dao.GroupDao;
import main.java.dao.StudentDao;
import main.java.implementation.CourseImplDao;
import main.java.implementation.GroupImplDao;
import main.java.implementation.StudentImplDao;
import main.java.model.Courses;
import main.java.model.Groups;
import main.java.model.Student;
import main.java.util.ConnectionUtils;
import main.java.util.QueryExecutor;

import java.util.List;
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

        GroupDao groupDao = new GroupImplDao(connectionUtils);
        groupDao.saveAll(generatorData.generateGroup(15));

        List<Groups> groupsList = groupDao.getAll();

        StudentDao studentDao = new StudentImplDao(connectionUtils);
        studentDao.saveAll(generatorData.generateStudents(200, groupsList));

        CourseDao courseDao = new CourseImplDao(connectionUtils);
        courseDao.saveAll(generatorData.generateCourses(20));

        List<Student> allStudents = studentDao.findAll();
        List<Courses> allCourses  = courseDao.findAll();

        for (Student student : allStudents){
            for (Courses course : allCourses){
                courseDao.addStudentsAndCourses(student, course);
            }
        }

    }

}
