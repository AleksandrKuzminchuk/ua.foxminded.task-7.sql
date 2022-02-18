package main.java.dao.constants;

public class QueryConstantsGroups {

    public static final String SAVE_ALL_GROUPS = "INSERT INTO groups (group_id, group_name) VALUES (?, ?)";

    public static final String FIND_BY_STUDENTS_COUNTS_LESS_EQUAL =
            "SELECT group_id, group_name, COUNT(students.student_id) " +
                    "FROM groups " +
                    "LEFT JOIN students " +
                    "ON students.group_id = groups.groups_id " +
                    "CROUP BY groups.group_id " +
                    "HAVING COUNT(*) <= ?" +
                    "ORDER BY groups.groups_id";
}
