package main.java.dao.constants;

public class QueryConstantsGroups {

    public static final String UPDATE_GROUP = "UPDATE public.groups SET group_name = ? WHERE group_id = ?";

    public static final String COUNT_GROUPS = "SELECT COUNT(*) FROM public.groups";

    public static final String DELETE_ALL_GROUPS = "DELETE FROM public.groups";

    public static final String DELETE_BY_ID_GROUP = "DELETE FROM public.groups WHERE group_id = ?";

    public static final String DELETE_GROUPS = "DELETE FROM public.groups WHERE group_name = ?";

    public static final String FIND_BY_GROUP_ID = "SELECT * FROM public.groups WHERE group_id = ?";

    public static final String SAVE_GROUP = "INSERT INTO groups (group_name) VALUES (?)";

    public static final String FIND_BY_STUDENTS_COUNTS_LESS_EQUAL =
            "SELECT groups.group_id, groups.group_name, COUNT(students.student_id) " +
                    "FROM public.groups " +
                    "LEFT JOIN students ON students.group_id = groups.group_id " +
                    "GROUP BY groups.group_id " +
                    "HAVING COUNT(*) <= ?" +
                    "ORDER BY groups.group_id";

    public static final String FIND_ALL = "SELECT groups.group_id, groups.group_name FROM groups";
}
