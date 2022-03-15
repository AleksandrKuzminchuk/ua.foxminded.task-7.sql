package main.java.dao.constants;

public class QueryConstantsStudents {

    public static final String SELECTION_BY_COURSE_NAME_QUERY_TEMPLATE =
            "SELECT students.student_id, students.group_id, students.first_name, students.last_name " +
                    "FROM students_courses INNER JOIN students " +
                    "ON students_courses.student_id = students.student_id " +
                    "INNER JOIN courses " +
                    "ON students_courses.course_id = courses.course_id " +
                    "WHERE courses.course_name = ?" +
                    "ORDER BY students.student_id";

    public static final String SAVE_STUDENT = "INSERT INTO students (first_name, last_name, group_id) VALUES (?, ?, ?)";
    public static final String FIND_BY_ID_STUDENT = "SELECT * FROM students WHERE student_id = ?";
    public static final String FIND_ALL_STUDENTS = "SELECT student_id, group_id, first_name, last_name FROM students";
    public static final String DELETE_BY_ID_STUDENT = "DELETE FROM students WHERE student_id = ?";
    public static final String ASSIGN_TO_COURSE = "INSERT INTO students_courses (course_id, student_id) VALUES (?, ?)";
    public static final String DELETE_FROM_COURSE = "DELETE FROM students_courses WHERE student_id = ? AND course_id = ?";
    public static final String COUNT_STUDENTS = "SELECT COUNT(*) FROM public.students";
    public static final String DELETE_STUDENT = "DELETE FROM public.students WHERE first_name = ? AND last_name = ?";
}

