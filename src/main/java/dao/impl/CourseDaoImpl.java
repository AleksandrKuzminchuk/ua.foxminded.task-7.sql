package main.java.dao.impl;

import main.java.dao.CourseDao;
import main.java.dao.constants.QueryConstantsCourses;
import main.java.exceptions.ExceptionsHandlingConstants;
import main.java.exceptions.NoDBPropertiesException;
import main.java.model.Courses;
import main.java.util.ConnectionUtils;
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
    public void saveAll(List<Courses> courses) {
        requiredNonNull(courses);
        logger.info(format("saving %d courses...", courses.size()));
        courses.forEach(this::save);
        logger.info(format("All %d courses saved SUCCESSFULLY", courses.size()));
    }

    @Override
    public List<Courses> findByStudentId(Integer studentId) {
        requiredNonNull(studentId);
        logger.info(format("findByStudentId('%d')", studentId));
        List<Courses> courses = new LinkedList<>();
        try (PreparedStatement statement = connectionUtils.getConnection().prepareStatement(QueryConstantsCourses.FIND_COURSES_BY_STUDENT_ID);
        ) {
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                courses.add(extract(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can't findByStudentId", e);
        }
        logger.info(format("%d course found", studentId));
        return courses;
    }

    @Override
    public Optional<Courses> save(Courses course) {
        requiredNonNull(course);
        logger.info(format("saving %s...", course));
        try (PreparedStatement statement = connectionUtils.getConnection()
                .prepareStatement(QueryConstantsCourses.SAVE_COURSE, new String[]{"course_id"})) {
            statement.setString(1, course.getCourseName());
            statement.setString(2, course.getCourseDescription());
            statement.executeUpdate();
            ResultSet generatedKey = statement.getGeneratedKeys();
            generatedKey.next();
            Integer course_id = generatedKey.getInt("course_id");
            course.setCourseId(course_id);
            Optional<Courses> result = Optional.of(course);
            logger.info(format("%s SAVED", course));
            return result;
        } catch (SQLException e) {
            logger.error("Can't save a course", e);
            throw new NoDBPropertiesException(e.getLocalizedMessage());
        }
    }


    @Override//    ToDo: I can't understand this method...
    public Optional<Courses> findById(Integer studentId) {
        requiredNonNull(studentId);
        logger.info(format("findById %d", studentId));
        try (PreparedStatement statement = connectionUtils.getConnection()
                .prepareStatement(QueryConstantsCourses.FIND_COURSES_BY_STUDENT_ID)) {
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            Courses result = resultSet.next() ? extract(resultSet) : null;
            logger.info(format("%s FOUND", result));
            return Optional.ofNullable(result);
        } catch (SQLException e) {
            logger.error("Can't find course by studentId", e);
            throw new NoDBPropertiesException(e.getLocalizedMessage());
        }
    }

    @Override
    public List<Courses> findAll() {
        List<Courses> courses = new LinkedList<>();
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

    private Courses extract(ResultSet resultSet) throws SQLException {
        requiredNonNull(resultSet);
        logger.info(format("extract with resultSet '%s'"));
        return new Courses(
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
        throw new NotImplementedException("Method 'existsById' not implemented");
    }

    @Override
    public void deleteById(Integer coursesId) {
        throw new NotImplementedException("Method 'count' not implemented");
    }

    @Override
    public void delete(Courses course) {
        throw new NotImplementedException("Method 'delete' not implemented");
    }

    @Override
    public void deleteAll() {
        throw new NotImplementedException("Method 'deleteAll' not implemented");
    }

    private void requiredNonNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException(ExceptionsHandlingConstants.ARGUMENT_IS_NULL);
        }
    }
}
