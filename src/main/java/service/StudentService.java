package main.java.service;

import main.java.dao.CourseDao;
import main.java.exceptions.ExceptionsHandlingConstants;
import main.java.exceptions.NoDBPropertiesException;
import main.java.model.Course;
import main.java.model.Student;
import main.java.util.ConnectionUtils;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.lang.String.format;

public class StudentService {

    private static final String SELECTION_BY_COURSE_ID_QUERY_TEMPLATE =
            "SELECT students.student_id, students.group_id, students.first_name, students.last_name " +
                    "FROM students_courses INNER JOIN students " +
                    "ON students_courses.student_id = students.student_id " +
                    "INNER JOIN courses " +
                    "ON students_courses.course_id = courses.course_id " +
                    "WHERE courses.course_id = ?" +
                    "ORDER BY students.student_id";

    private final CourseDao courseDao;
    private final Random random;
    private final ConnectionUtils connectionUtils;

    public static final Logger logger = Logger.getLogger(StudentService.class);

    public StudentService(CourseDao courseDao, Random random, ConnectionUtils connectionUtils) {
        this.random = random;
        this.courseDao = courseDao;
        this.connectionUtils = connectionUtils;
    }

    public void addStudentsOnCourses(List<Student> allStudents, List<Course> allCourses) {
        requiredNonNull(allStudents, allCourses);
        for (int i = 0; i < allStudents.size() + allCourses.size(); i++) {
            Student student = allStudents.get(random.nextInt(allStudents.size()));
            Course course = allCourses.get(random.nextInt(allCourses.size()));
            courseDao.addStudentsAndCourses(student, course);
        }
    }

    public List<Student> findAllSignedOnCourse(Course course) {
        // TODO find all students signed to required course
        // TODO (query to students_courses table +JOIN students)
        requiredNonNull(course);
        logger.info(format("findAllSignedOnCourseID = '%d'", course.getCourseId()));
        List<Student> students = new LinkedList<>();
        try(PreparedStatement statement = connectionUtils.getConnection().prepareStatement(SELECTION_BY_COURSE_ID_QUERY_TEMPLATE)
                ){
            statement.setInt(1, course.getCourseId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                students.add(extract(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can't findAllSignedOnCourse", e);
            throw new NoDBPropertiesException(e.getLocalizedMessage());
        }
        logger.info(format("FOUND Students signed on course - %d, %s", course.getCourseId(), students));
        return students;//ToDo-> implement me !
    }

    private Student extract(ResultSet resultSet) throws SQLException {
        requiredNonNull(resultSet);
        Student student = new Student(
                resultSet.getInt("student_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"));
        student.setGroupId(resultSet.getInt("group_id"));
        return student;
    }

    private void requiredNonNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException(ExceptionsHandlingConstants.ARGUMENT_IS_NULL);
        }
    }

    private void requiredNonNull(Object o, Object o2) {
        if (o == null || o2 ==null) {
            throw new IllegalArgumentException(ExceptionsHandlingConstants.ARGUMENT_IS_NULL);
        }
    }


}
