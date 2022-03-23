package dao.constants;

public class QueryConstantsCourses {

    public static final String SAVE_COURSE = "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";

    public static final String INSERTION_QUERY_TEMPLATE_IN_STUDENTS_COURSE_TABLE = "INSERT INTO students_courses (student_id, course_id) VALUES (?, ?)";

    public static final String FIND_ALL = "SELECT course_id, course_name, course_description FROM public.courses";

    public static final String FIND_COURSES_BY_STUDENT_ID =
            "SELECT courses.course_id, courses.course_name, courses.course_description " +
                    "FROM students_courses " +
                    "INNER JOIN courses " +
                    "ON students_courses.course_id = courses.course_id " +
                    "WHERE students_courses.student_id = ?";

    public static final String COUNT_COURSES = "SELECT COUNT(*) FROM public.courses";

    public static final String DELETE_BY_ID_COURSE = "DELETE FROM public.courses WHERE course_id = ?";

    public static final String DELETE_BY_COURSE = "DELETE FROM public.courses WHERE course_name = ?";

    public static final String DELETE_ALL_COURSES = "DELETE FROM public.courses";
}
