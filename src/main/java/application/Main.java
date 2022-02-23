package main.java.application;

import com.github.javafaker.Faker;
import main.java.config.PropertiesManager;
import main.java.dao.CourseDao;
import main.java.dao.GroupDao;
import main.java.dao.StudentDao;
import main.java.dao.impl.CourseDaoImpl;
import main.java.dao.impl.GroupDaoImpl;
import main.java.dao.impl.StudentDaoImpl;
import main.java.model.Course;
import main.java.model.Group;
import main.java.model.Student;
import main.java.service.StudentService;
import main.java.util.ConnectionUtils;
import main.java.util.QueryExecutor;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Random;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);
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
        groupDao.saveAll(generatorData.generateGroup(15));
        List<Group> allGroups = groupDao.findAll();

        StudentDao studentDao = new StudentDaoImpl(connectionUtils);
        studentDao.saveAll(generatorData.generateStudents(200, allGroups));

        CourseDao courseDao = new CourseDaoImpl(connectionUtils);
        courseDao.saveAll(generatorData.generateCourses(20));

         StudentService studentService = new StudentService(courseDao, random);
        studentService.addStudentsOnCourses(studentDao.findAll(), courseDao.findAll());

        List<Group> groupsByStudents = groupDao.findByStudentsCountsLessEqual(10);
        logger.info(groupsByStudents);

        List<Course> courses = courseDao.findAll();
        logger.info(courses);
    }

}
