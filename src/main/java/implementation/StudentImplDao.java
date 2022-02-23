package main.java.implementation;

import main.java.dao.StudentDao;
import main.java.dao.constants.QueryConstantsStudents;
import main.java.exceptions.ExceptionsHandlingConstants;
import main.java.exceptions.NoDBPropertiesException;
import main.java.model.Student;
import main.java.util.ConnectionUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

public class StudentImplDao implements StudentDao {

    private static final Logger logger = Logger.getLogger(StudentImplDao.class);

    private final ConnectionUtils connectionUtils;

    public StudentImplDao(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    @Override
    public Optional<Student> save(Student student) {
       requiredNonNull(student);
       logger.info(format("saving %s...", student));

       try(PreparedStatement statement = connectionUtils.getConnection()
               .prepareStatement(QueryConstantsStudents.SAVE_STUDENT, new String[]{"student_id"})
               ){
           statement.setString(1, student.getFirstName());
           statement.setString(2, student.getLastName());
           if (student.getGroupId() != null){
               statement.setInt(3, student.getGroupId());
           } else {
               statement.setNull(3, Types.INTEGER);
           }
           statement.executeUpdate();
           ResultSet generatedKey = statement.getGeneratedKeys();
           generatedKey.next();
           Integer studentId = generatedKey.getInt("student_id");
           student.setStudentId(studentId);
           Optional<Student> result = Optional.of(student);
           logger.info(format("%s SAVED", student));
           return result;
       } catch (SQLException e) {
           logger.error("Can't save student", e);
           throw new NoDBPropertiesException(e.getLocalizedMessage());
       }
    }

    @Override
    public Optional<Student> findById(Integer studentId) {
        requiredNonNull(studentId);

        logger.info(format("findById('%d')", studentId));
        try(PreparedStatement statement = connectionUtils.getConnection().prepareStatement(QueryConstantsStudents.FIND_BY_ID_STUDENT);
                ){
            statement.setInt(1,studentId);
            ResultSet resultSet = statement.executeQuery();
            Student result = resultSet.next() ? extract(resultSet) : null;
            logger.info(format("%s FOUND", result));
            return Optional.ofNullable(result);
        } catch (SQLException e) {
            logger.error("Can't find student by Id",e);
            throw new NoDBPropertiesException(e.getLocalizedMessage());
        }
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new LinkedList<>();
        logger.info("findAll...");
        try(PreparedStatement statement = connectionUtils.getConnection().prepareStatement(QueryConstantsStudents.FIND_ALL_STUDENTS);
                ){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                students.add(extract(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can't find students", e);
        }
        logger.info(format("%d students found", students.size()));
        return students;
    }

    @Override
    public void deleteById(Integer studentId) {
        requiredNonNull(studentId);
        logger.info(format("deleteById('%d')", studentId));
        try(PreparedStatement statement = connectionUtils.getConnection().prepareStatement(QueryConstantsStudents.DELETE_BY_ID_STUDENT)
                ){
            statement.setInt(1, studentId);
            statement.executeUpdate();
            logger.info(format("student with id '%d' DELETED", studentId));
        } catch (SQLException e) {
            logger.error("Can't delete student by id '%d'",e);
            throw new NoDBPropertiesException(e.getLocalizedMessage());
        }

    }

    @Override
    public void saveAll(List<Student> students) {
        requiredNonNull(students);
        logger.info(format("saving %d students...", students.size()));
        students.forEach(this::save);
        logger.info(format("All %d students saved SUCCESSFULLY", students.size()));
    }

    @Override
    public List<Student> findByCourseName(String courseName) {
        requiredNonNull(courseName);
        logger.info(format("findByCourseName('%s')", courseName));

        List<Student> students = new LinkedList<>();

        try(PreparedStatement statement = connectionUtils.getConnection().prepareStatement(QueryConstantsStudents.SELECTION_BY_COURSE_NAME_QUERY_TEMPLATE);
                ){
            statement.setString(1, courseName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                students.add(extract(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can't find students by course name");
            throw new NoDBPropertiesException(e.getLocalizedMessage());
        }
        return students;
    }

    @Override
    public void assignToCourse(Integer studentId, Integer courseId) {
        logger.info(format("Assigning a studentId('%d') to a courseId('%d')", studentId, courseId));
        try (PreparedStatement statement = connectionUtils.getConnection().prepareStatement(QueryConstantsStudents.ASSIGN_TO_COURSE)
                ){
            statement.setInt(1, courseId);
            statement.setInt(2, studentId);
            statement.executeUpdate();
            logger.info(format("Assigned a studentId('%d') to a courseId('%d') SUCCESSFULLY", studentId, courseId));
        } catch (SQLException e) {
            logger.error("Can't assign a studentId to a courseId", e);
            throw new NoDBPropertiesException(e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteFromCourse(Integer studentId, Integer courseId) {
        logger.info(format("delete from course a studentId('%d') to a courseId('%d')", studentId,  courseId));
        try (PreparedStatement statement = connectionUtils.getConnection().prepareStatement(QueryConstantsStudents.DELETE_FROM_COURSE);
                ){
            statement.setInt(1, courseId);
            statement.setInt(2, studentId);
            statement.executeUpdate();
            logger.info(format("deleted from course a studentId('%d') to a courseId('%d') SUCCESSFULLY", studentId, courseId));
        } catch (SQLException e) {
            logger.error("Can't deleteFromCourse a studentId to a courseId", e);
        }
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

    @Override
    public boolean existsById(Integer integer) {
        throw new NotImplementedException("Method 'existsById' not implemented");
    }

    @Override
    public long count() {
        throw new NotImplementedException("Method 'count' not implemented");
    }

    @Override
    public void delete(Student entity) {
        throw new NotImplementedException("Method 'delete' not implemented");
    }

    @Override
    public void deleteAll() {
        throw new NotImplementedException("Method 'deleteAll' not implemented");
    }

    private void requiredNonNull(Object o){
        if (o == null){
            throw new IllegalArgumentException(ExceptionsHandlingConstants.ARGUMENT_IS_NULL);
        }
    }
}
