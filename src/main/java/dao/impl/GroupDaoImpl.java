package main.java.dao.impl;

import main.java.dao.GroupDao;
import main.java.exceptions.ExceptionsHandlingConstants;
import main.java.exceptions.NoDBPropertiesException;
import main.java.model.Groups;
import main.java.util.ConnectionUtils;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static java.lang.String.format;

public class GroupDaoImpl implements GroupDao {

    public static final String SAVE_ALL_GROUPS = "INSERT INTO groups (group_name) VALUES (?)";
    public static final String FIND_BY_STUDENTS_COUNTS_LESS_EQUAL =
            "SELECT group_id, group_name, COUNT(students.student_id) " +
                    "FROM groups " +
                    "LEFT JOIN students ON students.group_id = groups.group_id " +
                    "GROUP BY groups.group_id " +
                    "HAVING COUNT(*) <= ?" +
                    "ORDER BY groups.group_id";
    private static final Logger logger = Logger.getLogger(GroupDaoImpl.class);

    private final ConnectionUtils connectionUtils;

    public GroupDaoImpl(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    @Override
    public void saveAll(List<Groups> groups) {
        requiredNonNull(groups);
        logger.info(format("saving %d groups...", groups.size()));
        logger.info(format("saving %s...", groups));
        try (PreparedStatement statement = connectionUtils.getConnection().prepareStatement(SAVE_ALL_GROUPS)) {
            for (Groups group : groups) {
                statement.setString(1, group.getGroupName());
                statement.addBatch();
                logger.info(format("%s SAVED", groups));
            }
            statement.executeBatch();
            logger.info(format("All %d groups saved SUCCESSFULLY", groups.size()));
        } catch (SQLException e) {
            logger.error("Can't save all groups", e);
        }


    }

    @Override
    public List<Groups> findByStudentsCountsLessEqual(Integer count) {
        requiredNonNull(count);
        logger.info(format("findByStudentsCountsLessEqual('%d')", count));
        List<Groups> groups = new LinkedList<>();

        try (PreparedStatement statement = connectionUtils.getConnection().prepareStatement(FIND_BY_STUDENTS_COUNTS_LESS_EQUAL)) {
            statement.setInt(1, count);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                groups.add(new Groups(resultSet.getInt(1), resultSet.getString(2)));
            }
            logger.info(format("foundByStudentsCountsLessEqual('%d') SUCCESSFULLY", count));
        } catch (SQLException e) {
            logger.error("Can't findByStudentsCountsLessEqual", e);
            throw new NoDBPropertiesException(e.getLocalizedMessage());
        }
        return groups;
    }

    private void requiredNonNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException(ExceptionsHandlingConstants.ARGUMENT_IS_NULL);
        }
    }
}
