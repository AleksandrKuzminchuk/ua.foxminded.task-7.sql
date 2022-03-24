package service.impl;

import dao.CourseDao;
import dao.StudentDao;
import exceptions.ExceptionsHandlingConstants;
import model.Course;
import model.Student;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.log4j.Logger;
import service.StudentService;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

public class StudentServiceImpl implements StudentService {

    public static final Logger logger = Logger.getLogger(StudentServiceImpl.class);

    private final StudentDao studentDao;
    private final CourseDao courseDao;

    public StudentServiceImpl(CourseDao courseDao, StudentDao studentDao) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
    }

    public void addStudentOnCourses(Student student, List<Course> courses) {
        requiredNonNull(student, courses);
        logger.info(format("ADDING... %s on %s", student, courses));
        courses.forEach(course -> studentDao.addStudentAndCourse(student, course));
        logger.info(format("ADDED %s on %s", student, courses));
    }

    public List<Student> findAllSignedOnCourse(Integer courseId) {
        requiredNonNull(courseId);
        logger.info(format("findAllSignedOnCourseID = '%d'", courseId));
        List<Student> students = studentDao.findAllSignedOnCourse(courseId);
        logger.info(format("FOUND Students signed on course - %d, %s", courseId, students));
        return students;
    }

    @Override
    public List<Student> findByCourseName(String courseName) {
        requiredNonNull(courseName);
        logger.info(format("findByCourseName('%s')", courseName));
        return studentDao.findByCourseName(courseName);
    }

    @Override
    public void assignToCourse(Integer studentId, Integer courseId) {
        logger.info(format("Assigning a studentId('%d') to a courseId('%d')", studentId, courseId));
        studentDao.assignToCourse(studentId, courseId);
        logger.info(format("Assigned a studentId('%d') to a courseId('%d') SUCCESSFULLY", studentId, courseId));
    }

    @Override
    public void deleteFromCourse(Integer studentId, Integer courseId) {
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

    public void updateStudent(Student student){
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
        return studentDao.save(entity);
    }

    @Override
    public Optional<Student> findById(Integer integer) {
        requiredNonNull(integer);
        logger.info(format("findById('%d')", integer));
        logger.info(format("%d FOUND", integer));
        return studentDao.findById(integer);

    }

    @Override
    public boolean existsById(Integer integer) {
        throw new NotImplementedException("Method 'existsById' not implemented");
    }

    @Override
    public List<Student> findAll() {
        logger.info("findAll...");
        return studentDao.findAll();
    }

    @Override
    public long count() {
        logger.info("find count students...");
        return studentDao.count();
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

    private void requiredNonNull(Object o, Object o2) {
        if (o == null && o2 == null) {
            throw new IllegalArgumentException(ExceptionsHandlingConstants.ARGUMENT_IS_NULL);
        }
    }
}
