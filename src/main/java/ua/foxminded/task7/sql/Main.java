package ua.foxminded.task7.sql;

import ua.foxminded.task7.sql.util.GeneratorData;
import com.github.javafaker.Faker;
import ua.foxminded.task7.sql.config.PropertiesManager;
import ua.foxminded.task7.sql.dao.CourseDao;
import ua.foxminded.task7.sql.dao.GroupDao;
import ua.foxminded.task7.sql.dao.StudentDao;
import ua.foxminded.task7.sql.dao.impl.CourseDaoImpl;
import ua.foxminded.task7.sql.dao.impl.GroupDaoImpl;
import ua.foxminded.task7.sql.dao.impl.StudentDaoImpl;
import ua.foxminded.task7.sql.model.Course;
import ua.foxminded.task7.sql.model.Group;
import ua.foxminded.task7.sql.model.Student;
import org.apache.log4j.Logger;
import ua.foxminded.task7.sql.service.impl.CourseServiceImpl;
import ua.foxminded.task7.sql.service.impl.GroupServiceImpl;
import ua.foxminded.task7.sql.service.impl.StudentServiceImpl;
import ua.foxminded.task7.sql.util.ConnectionUtils;
import ua.foxminded.task7.sql.util.QueryExecutor;

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
        groupDao.saveAll(generatorData.generateGroups(15));
        List<Group> allGroups = groupDao.findAll();
        List<Group> groupsByStudents = groupDao.findByStudentsCountsLessEqual(1);
        logger.info(groupsByStudents);


        StudentDao studentDao = new StudentDaoImpl(connectionUtils);
        studentDao.saveAll(generatorData.generateStudents(200, allGroups));

        CourseDao courseDao = new CourseDaoImpl(connectionUtils);
        courseDao.saveAll(generatorData.generateCourses(20));

        List<Student> students = studentDao.findAll();
        List<Course> courses = courseDao.findAll();

        GroupServiceImpl groupService = new GroupServiceImpl(groupDao);

        CourseServiceImpl courseService = new CourseServiceImpl(courseDao);


        StudentServiceImpl studentService = new StudentServiceImpl(studentDao);
        studentService.addStudentOnCourses(students.get(1), courses);

        List<Student> allSignedOnCurse = studentService.findAllSignedOnCourse(courses.get(courses.size() / 2).getCourseId());
        if (allSignedOnCurse == null || allSignedOnCurse.isEmpty()) {
        logger.error("allSignedOnCurse IS null  ->  must be Implemented!");
        }

        groupService.count();
        List<Group> lessEqual = groupService.findByStudentsCountsLessEqual(8);

        Optional<String> courseName = Optional.of(courses).map(list -> list.get(5)).map(Course::getCourseName);
        if (courseName.isPresent()) {
            List<Student> byCourseName = studentService.findByCourseName(courseName.get());
            logger.info("byCourseName:" + byCourseName);
        }
        studentService.deleteFromCourse(152, 2);

        studentService.findById(1);

        courseService.count();

        studentService.updateStudent(new Student(2,"Oleksandr", "Kuzminchuk"));

        courseService.updateCourse(new Course(1, "Match of Business", "Course of Master of Match"));

        groupService.updateGroup(new Group(1, "Kn-56"));

    }

}
