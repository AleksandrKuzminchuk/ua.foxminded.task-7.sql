package service.impl;

import java.util.List;
import java.util.Optional;

import dao.CourseDao;
import exceptions.ExceptionsHandlingConstants;
import model.Course;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.log4j.Logger;
import service.CourseService;

import static java.lang.String.format;

public class CourseServiceImpl implements CourseService {

    public static final Logger logger = Logger.getLogger(CourseServiceImpl.class);

    private final CourseDao courseDao;


    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;

    }

    @Override
    public Optional<Course> save(Course entity) {
        requiredNonNull(entity);
        logger.info(format("saving %s...", entity));
        logger.info(format("%s SAVED", entity));
        return courseDao.save(entity);
    }

    @Override
    public Optional<Course> findById(Integer integer) {
        requiredNonNull(integer);
        logger.info(format("findById %d", integer));
        return courseDao.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        throw new NotImplementedException("Method 'existsById' not implemented");
    }

    @Override
    public List<Course> findAll() {
        logger.info("findAll...");
        return courseDao.findAll();
    }

    @Override
    public long count() {
        logger.info("find count courses...");
        return courseDao.count();
    }

    @Override
    public void deleteById(Integer integer) {
        requiredNonNull(integer);
        logger.info(format("deleteById ('%d') course", integer));
        courseDao.deleteById(integer);
        logger.info(format("course with id '%d' DELETED", integer));
    }

    @Override
    public void delete(Course entity) {
        requiredNonNull(entity);
        logger.info(format("delete course by name '%s'", entity.getCourseName()));
        courseDao.delete(entity);
        logger.info(format("delete course by name '%s'", entity.getCourseName()));
    }

    @Override
    public void deleteAll() {
        logger.info("delete all courses...");
        courseDao.deleteAll();
        logger.info("DELETED ALL courses");
    }

    @Override
    public void updateCourse(Course course) {
        requiredNonNull(course);
        logger.info(format("UPDATE course by ID - %d",course.getCourseId()));
        courseDao.updateCourse(course);
        logger.info(format("UPDATED %s SUCCESSFULLY", course));
    }

    @Override
    public void saveAll(List<Course> courses) {
        logger.info(format("saving %d courses...", courses.size()));
        courseDao.saveAll(courses);
        logger.info(format("All %d courses saved SUCCESSFULLY", courses.size()));
    }

    @Override
    public List<Course> findByStudentId(Integer studentId) {
        requiredNonNull(studentId);
        logger.info(format("find by studentId('%d')", studentId));
        return courseDao.findByStudentId(studentId);
    }

    private void requiredNonNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException(ExceptionsHandlingConstants.ARGUMENT_IS_NULL);
        }
    }
}
