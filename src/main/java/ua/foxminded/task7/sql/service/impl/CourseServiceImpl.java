package ua.foxminded.task7.sql.service.impl;

import java.util.List;
import java.util.Optional;

import ua.foxminded.task7.sql.dao.CourseDao;
import ua.foxminded.task7.sql.exceptions.ExceptionsHandlingConstants;
import ua.foxminded.task7.sql.exceptions.NotFoundException;
import ua.foxminded.task7.sql.model.Course;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.log4j.Logger;
import ua.foxminded.task7.sql.service.CourseService;

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
        return Optional.ofNullable(courseDao.save(entity).orElseThrow(() -> new NotFoundException("Can't save course " + entity)));
    }

    @Override
    public Optional<Course> findById(Integer integer) {
        requiredNonNull(integer);
        logger.info(format("Find course by id - %d", integer));
        Optional<Course> course = Optional.ofNullable(courseDao.findById(integer).
                orElseThrow(() -> new NotFoundException("Course not found by id - " + integer)));
        logger.info(format("Found course %s by id - %d", course,integer));
        return course;
    }

    @Override
    public boolean existsById(Integer integer) {
        throw new NotImplementedException("Method 'existsById' not implemented");
    }

    @Override
    public List<Course> findAll() {
        logger.info("findAll...");
        List<Course> courses = courseDao.findAll();
        if (courses == null){
            throw new NotFoundException("Can't find all courses");
        }
        logger.info(format("Found all courses %s",courses));
        return courses;
    }

    @Override
    public long count() {
        logger.info("find count courses...");
        long count = courseDao.count();
        logger.info(format("Found count courses - %d", count));
        return count;
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
        requiredNonNull(courses);
        logger.info(format("saving %d courses...", courses.size()));
        courseDao.saveAll(courses);
        logger.info(format("All %d courses saved SUCCESSFULLY", courses.size()));
    }

    @Override
    public List<Course> findByStudentId(Integer studentId) {
        requiredNonNull(studentId);
        logger.info(format("Find courses by student Id('%d')", studentId));
        List<Course> courses = courseDao.findByStudentId(studentId);
        if (courses == null){
            throw new NotFoundException(format("Can't find courses by student Id - %d", studentId));
        }
        return courses;
    }

    private void requiredNonNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException(ExceptionsHandlingConstants.ARGUMENT_IS_NULL);
        }
    }
}
