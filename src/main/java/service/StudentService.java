package service;

import dao.CourseDao;
import dao.StudentDao;
import model.Course;
import model.Student;
import org.apache.log4j.Logger;

import java.util.List;

import static java.lang.String.format;

public class StudentService {

    public static final Logger logger = Logger.getLogger(StudentService.class);

    private final CourseDao courseDao;
    private final StudentDao studentDao;

    public StudentService(CourseDao courseDao, StudentDao studentDao) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
    }

    public void addStudentOnCourses(Student student, List<Course> courses) {
        if (courses != null && !courses.isEmpty())
            courses.forEach(course -> courseDao.addStudentAndCourse(student, course));
    }

    public List<Student> findAllSignedOnCourse(Integer courseId) {
        // TODO find all students signed to required course
        // TODO (query to students_courses table +JOIN students)
        logger.info(format("findAllSignedOnCourseID = '%d'", courseId));
        List<Student> students = studentDao.findAllSignedOnCourse(courseId);
        logger.info(format("FOUND Students signed on course - %d, %s", courseId, students));
        return students;
    }

}
