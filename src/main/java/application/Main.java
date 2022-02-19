package main.java.application;

import com.github.javafaker.Faker;
import main.java.config.PropertiesManager;
import main.java.dao.CourseDao;
import main.java.dao.GroupDao;
import main.java.dao.StudentDao;
import main.java.exceptions.NoDBPropertiesException;
import main.java.model.Student;
import main.java.dao.impl.CourseDaoImpl;
import main.java.dao.impl.GroupDaoImpl;
import main.java.dao.impl.StudentDaoImpl;
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

        GroupDao groupDao = new GroupDaoImpl(connectionUtils);
        groupDao.saveAll(generatorData.generateGroup(10));

        CourseDao courseDao = new CourseDaoImpl(connectionUtils);
        courseDao.saveAll(generatorData.generateCourses(20));

        StudentDao studentDao = new StudentDaoImpl(connectionUtils);
        studentDao.saveAll(generatorData.generateStudents(200));

        List<Student> allStudents = studentDao.findAll();
        if (allStudents == null || allStudents.isEmpty()) {
            throw new NoDBPropertiesException("Shit happened! Check 'studentDao.saveAll' method");
        }

        Optional<Student> student = studentDao.findById(14);
        studentDao.deleteById(12);
        studentDao.findById(12);
        courseDao.findById(10);

    }

}
