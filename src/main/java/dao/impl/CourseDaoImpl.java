package dao.impl;

import dao.CourseDao;
import dao.constants.QueryConstantsCourses;
import exceptions.ExceptionsHandlingConstants;
import exceptions.NoDBPropertiesException;
import model.Course;
import model.Student;
import util.ConnectionUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

public class CourseDaoImpl implements CourseDao {

    private static final Logger logger = Logger.getLogger(CourseDaoImpl.class);

    private final ConnectionUtils connectionUtils;

    public CourseDaoImpl(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    @Override
    public void saveAll(List<Course> courses) {
        requiredNonNull(courses);
        logger.info(format("saving %d courses...", courses.size()));
        courses.forEach(this::save);
        logger.info(format("All %d courses saved SUCCESSFULLY", courses.size()));
    }

    @Override
    public void addStudentAndCourse(Student student, Course course) {
        requiredNonNull(student, course);
        logger.info(format("Adding student_id '%s' and course_id '%s' to students_courses table", student.getStudentId(), course.getCourseId()));
        try (PreparedStatement statement = connectionUtils.getConnection()
                .prepareStatement(QueryConstantsCourses.INSERTION_QUERY_TEMPLATE_IN_STUDENTS_COURSE_TABLE)) {
            statement.setInt(1, student.getStudentId());
            statement.setInt(2, course.getCourseId());
            statement.executeUpdate();
            logger.info("Added SUCCESSFULLY");
        } catch (SQLException e) {
            logger.error("Can't add students and courses");
            throw new NoDBPropertiesException(e.getLocalizedMessage());
        }
    }

    @Override
    public List<Course> findByStudentId(Integer studentId) {
        requiredNonNull(studentId);
        logger.info(format("find by studentId('%d')", studentId));
        List<Course> courses = new LinkedList<>();
        try (PreparedStatement statement = connectionUtils.getConnection().prepareStatement(QueryConstantsCourses.FIND_COURSES_BY_STUDENT_ID);
        ) {
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                courses.add(extract(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can't find by studentId", e);
        }
        logger.info(format("%d course found", studentId));
        return courses;
    }

    @Override
    public Optional<Course> save(Course course) {
        requiredNonNull(course);
        logger.info(format("saving %s...", course));
        try (PreparedStatement statement = connectionUtils.getConnection()
                .prepareStatement(QueryConstantsCourses.SAVE_COURSE, new String[]{"course_id"})) {
            statement.setString(1, course.getCourseName());
            statement.setString(2, course.getCourseDescription());
            statement.executeUpdate();

            ResultSet generatedKey = statement.getGeneratedKeys();
            generatedKey.next();

            Integer courseId = generatedKey.getInt("course_id");
            course.setCourseId(courseId);
            Optional<Course> result = Optional.of(course);
            logger.info(format("%s SAVED", course));
            return result;
        } catch (SQLException e) {
            logger.error("Can't save a course", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Course> findById(Integer studentId) {
        requiredNonNull(studentId);
        logger.info(format("findById %d", studentId));
        try (PreparedStatement statement = connectionUtils.getConnection()
                .prepareStatement(QueryConstantsCourses.FIND_COURSES_BY_STUDENT_ID)) {
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            Course result = resultSet.next() ? extract(resultSet) : null;
            logger.info(format("%s FOUND", result));
            return Optional.ofNullable(result);
        } catch (SQLException e) {
            logger.error("Can't find course by studentId", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Course> findAll() {
        List<Course> courses = new LinkedList<>();
        logger.info("findAll...");
        try (PreparedStatement statement = connectionUtils.getConnection()
                .prepareStatement(QueryConstantsCourses.FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                courses.add(extract(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can't find all courses", e);
        }
        logger.info(format("%d courses found", courses.size()));
        return courses;
    }

    private Course extract(ResultSet resultSet) throws SQLException {
        return new Course(
                resultSet.getInt("course_id"),
                resultSet.getString("course_name"),
                resultSet.getString("course_description"));
    }

    @Override
    public boolean existsById(Integer courseId) {
        throw new NotImplementedException("Method 'existsById' not implemented");
    }

    @Override
    public long count() {
        logger.info("find count courses...");
        long result = 0;
        try(PreparedStatement statement = connectionUtils.getConnection().prepareStatement(
                QueryConstantsCourses.COUNT_COURSES)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                result = resultSet.getLong("count");
            }
            logger.info(format("FOUND COUNT (%d) courses", result));
            return result;
        } catch (SQLException e) {
            logger.error("Can't find count courses");
            throw new NotImplementedException(e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteById(Integer coursesId) {
        requiredNonNull(coursesId);
        logger.info(format("deleteById ('%d') course", coursesId));
        try(PreparedStatement statement = connectionUtils.getConnection().prepareStatement(
                QueryConstantsCourses.DELETE_BY_ID_COURSE)
                ){
            statement.setInt(1, coursesId);
            statement.executeUpdate();
            logger.info(format("course with id '%d' DELETED", coursesId));
        }catch (SQLException e){
            logger.error("Can't delete course by id '%d'", e);
            throw new NoDBPropertiesException(e.getLocalizedMessage());
        }
    }

    @Override
    public void delete(Course course) {
        requiredNonNull(course);
        logger.info(format("delete course by name '%s'", course.getCourseName()));
        try(PreparedStatement statement = connectionUtils.getConnection().prepareStatement(
                QueryConstantsCourses.DELETE_BY_COURSE)
                ){
            statement.setString(1, course.getCourseName());
            statement.executeUpdate();
            logger.info(format("delete course by name '%s'", course.getCourseName()));
        }catch (SQLException e){
            logger.error("Can't delete course by name", e);
            throw new NoDBPropertiesException(e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteAll() {
        logger.info("delete all courses...");
        try(PreparedStatement statement = connectionUtils.getConnection().prepareStatement(
                QueryConstantsCourses.DELETE_ALL_COURSES)
                ){
            statement.executeUpdate();
            logger.info("DELETED ALL courses");
        }catch (SQLException e){
            logger.error("Can't delete all courses", e);
            throw new NoDBPropertiesException(e.getLocalizedMessage());
        }
    }

    private void requiredNonNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException(ExceptionsHandlingConstants.ARGUMENT_IS_NULL);
        }
    }

    private void requiredNonNull(Object o, Object o2) {
        if (o == null || o2 == null) {
            throw new IllegalArgumentException(ExceptionsHandlingConstants.ARGUMENT_IS_NULL);
        }
    }
}
