package main.java;

import com.github.javafaker.Faker;
import main.java.application.GeneratorData;
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
import java.util.Optional;
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
        List<Group> groupsByStudents = groupDao.findByStudentsCountsLessEqual(1);
        logger.info(groupsByStudents);


        StudentDao studentDao = new StudentDaoImpl(connectionUtils);
        studentDao.saveAll(generatorData.generateStudents(200, allGroups));

        CourseDao courseDao = new CourseDaoImpl(connectionUtils);
        courseDao.saveAll(generatorData.generateCourses(20));

        List<Student> students = studentDao.findAll();
        List<Course> courses = courseDao.findAll();


        StudentService studentService = new StudentService(courseDao, random, connectionUtils);
        studentService.addStudentsOnCourses(students, courses);

        List<Student> allSignedOnCurse = studentService.findAllSignedOnCourse(courses.get(courses.size() / 2));
        if (allSignedOnCurse == null || allSignedOnCurse.isEmpty()) {
        logger.error("allSignedOnCurse IS null  ->  must be Implemented!");
        }

        groupDao.count();
        List<Group> lessEqual = groupDao.findByStudentsCountsLessEqual(8);

        Optional<String> courseName = Optional.of(courses).map(list -> list.get(5)).map(Course::getCourseName);
        if (courseName.isPresent()) {
            List<Student> byCourseName = studentDao.findByCourseName(courseName.get());
            logger.info("byCourseName:" + byCourseName);
        }
        studentDao.deleteFromCourse(152, 2);

        courseDao.findById(1);

        courseDao.count();

        studentDao.delete(new Student("Bennie", "Sauer"));

    }

}
