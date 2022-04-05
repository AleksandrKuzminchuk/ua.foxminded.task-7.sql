package ua.foxminded.task7.sql.service.impl;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.log4j.Logger;
import ua.foxminded.task7.sql.dao.StudentDao;
import ua.foxminded.task7.sql.exceptions.ExceptionsHandlingConstants;
import ua.foxminded.task7.sql.exceptions.NotFoundException;
import ua.foxminded.task7.sql.model.Course;
import ua.foxminded.task7.sql.model.Student;
import ua.foxminded.task7.sql.service.StudentService;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

public class StudentServiceImpl implements StudentService {

    public static final Logger logger = Logger.getLogger(StudentServiceImpl.class);

    private final StudentDao studentDao;

    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public void addStudentOnCourses(Student student, List<Course> courses) {
        requiredNonNull(student);
        requiredNonNull(courses);
        logger.info(format("ADDING... %s on %s", student, courses));
        courses.forEach(course -> studentDao.addStudentOnCourse(student, course));
        logger.info(format("ADDED %s on %s", student, courses));
    }

    public List<Student> findAllSignedOnCourse(Integer courseId) {
        requiredNonNull(courseId);
        logger.info(format("findAllSignedOnCourseID = '%d'", courseId));
        List<Student> students = studentDao.findAllSignedOnCourse(courseId);
        logger.info(format("FOUND Students signed on course - %d, %s", courseId, students));
        if (students == null) {
            throw new NotFoundException(format("Can't find all students on course %d", courseId));
        }
        logger.info(format("Found students signed on course %d %s",courseId,students));
        return students;
    }

    @Override
    public List<Student> findByCourseName(String courseName) {
        requiredNonNull(courseName);
        logger.info(format("findByCourseName('%s')", courseName));
        List<Student> students = studentDao.findByCourseName(courseName);
        if (students == null){
            throw new NotFoundException(format("Can't find students by course name %s",courseName));
        }
        logger.info(format("Found students by course name %s %s", courseName, students));
        return students;
    }

    @Override
    public void assignToCourse(Integer studentId, Integer courseId) {
        requiredNonNull(studentId);
        requiredNonNull(courseId);
        logger.info(format("Assigning a studentId('%d') to a courseId('%d')", studentId, courseId));
        studentDao.assignToCourse(studentId, courseId);
        logger.info(format("Assigned a studentId('%d') to a courseId('%d') SUCCESSFULLY", studentId, courseId));
    }

    @Override
    public void deleteFromCourse(Integer studentId, Integer courseId) {
        requiredNonNull(studentId);
        requiredNonNull(courseId);
        logger.info(format("delete from course a studentId('%d') to a courseId('%d')", studentId, courseId));
        studentDao.deleteFromCourse(studentId, courseId);
        logger.info(format("deleted from course a studentId('%d') to a courseId('%d') SUCCESSFULLY", studentId, courseId));
    }

    @Override
    public void saveAll(List<Student> students) {
        requiredNonNull(students);
        logger.info(format("saving %d students...", students.size()));
        studentDao.saveAll(students);
        logger.info(format("All %d students saved SUCCESSFULLY", students.size()));
    }

    public void updateStudent(Student student) {
        requiredNonNull(student);
        logger.info(format("UPDATE student by ID-%d from StudentServiceImpl", student.getStudentId()));
        studentDao.updateStudent(student);
        logger.info(format("UPDATED %s from StudentServiceImpl", student));
    }


    @Override
    public Optional<Student> save(Student entity) {
        requiredNonNull(entity);
        logger.info(format("saving %s...", entity));
        logger.info(format("%s SAVED", entity));
        return Optional.ofNullable(studentDao.save(entity).orElseThrow(() -> new NotFoundException("Can't save student " + entity)));
    }

    @Override
    public Optional<Student> findById(Integer integer) {
        requiredNonNull(integer);
        logger.info(format("findById('%d')", integer));
        Optional<Student> student = Optional.ofNullable(studentDao.findById(integer).
                orElseThrow(() -> new NotFoundException("Student not found by id = " + integer)));
        logger.info(format("FOUND student %s by Id - %d", student,integer));
        return student;

    }

    @Override
    public boolean existsById(Integer integer) {
        throw new NotImplementedException("Method 'existsById' not implemented");
    }

    @Override
    public List<Student> findAll() {
        logger.info("findAll...");
        List<Student> students = studentDao.findAll();
        if (students == null){
            throw new NotFoundException("Can't find all students");
        }
        logger.info(format("Found all students %s", students));
        return students;
    }

    @Override
    public long count() {
        logger.info("find count students...");
        long count = studentDao.count();
        logger.info(format("Found count students = %d", count));
        return count;
    }

    @Override
    public void deleteById(Integer integer) {
        requiredNonNull(integer);
        logger.info(format("deleteById('%d')", integer));
        studentDao.deleteById(integer);
        logger.info(format("student with id '%d' DELETED", integer));
    }

    @Override
    public void delete(Student entity) {
        requiredNonNull(entity);
        logger.info(format("delete student by name and surname (%s %s)", entity.getFirstName(), entity.getLastName()));
        studentDao.delete(entity);
        logger.info(format("deleted student by name and surname (%s %s)", entity.getFirstName(), entity.getLastName()));
    }

    @Override
    public void deleteAll() {
        logger.info("DELETE ALL STUDENTS");
        studentDao.deleteAll();
        logger.info("DELETED ALL STUDENTS");
    }

    private void requiredNonNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException(ExceptionsHandlingConstants.ARGUMENT_IS_NULL);
        }
    }

}
