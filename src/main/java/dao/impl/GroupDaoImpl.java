package main.java.dao.impl;

import main.java.dao.GroupDao;
import main.java.exceptions.ExceptionsHandlingConstants;
import main.java.exceptions.NoDBPropertiesException;
import main.java.model.Group;
import main.java.util.ConnectionUtils;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

public class GroupDaoImpl implements GroupDao {

    public static final String SAVE_ALL_GROUPS = "INSERT INTO groups (group_name) VALUES (?)";
    public static final String FIND_BY_STUDENTS_COUNTS_LESS_EQUAL =
            "SELECT groups.group_id, groups.group_name, COUNT(students.student_id) " +
                    "FROM public.groups " +
                    "LEFT JOIN students ON students.group_id = groups.group_id " +
                    "GROUP BY groups.group_id " +
                    "HAVING COUNT(*) <= ?" +
                    "ORDER BY groups.group_id";
    private static final Logger logger = Logger.getLogger(GroupDaoImpl.class);
    private static final String FIND_ALL = "SELECT groups.group_id, groups.group_name FROM groups";

    private final ConnectionUtils connectionUtils;

    public GroupDaoImpl(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    @Override
    public void saveAll(List<Group> groups) {
        requiredNonNull(groups);
        logger.info(format("saving %d groups...", groups.size()));
        logger.info(format("saving %s...", groups));
        try (PreparedStatement statement = connectionUtils.getConnection().prepareStatement(SAVE_ALL_GROUPS)) {
            for (Group group : groups) {
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
    public List<Group> findByStudentsCountsLessEqual(Integer count) {
        requiredNonNull(count);
        logger.info(format("findByStudentsCountsLessEqual('%d')", count));
        List<Group> groups = new LinkedList<>();

        try (PreparedStatement statement = connectionUtils.getConnection()
                .prepareStatement(FIND_BY_STUDENTS_COUNTS_LESS_EQUAL)) {
            statement.setInt(1, count);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                groups.add(new Group(resultSet.getInt(1), resultSet.getString(2)));
            }
            logger.info(format("found '%d' group", groups.size()));
        } catch (SQLException e) {
            logger.error("Can't findByStudentsCountsLessEqual", e);
            throw new NoDBPropertiesException(e.getLocalizedMessage());
        }
        return groups;
    }

    @Override
    public List<Group> findAll() {
        logger.info("getAll()...");
        List<Group> groups = new LinkedList<>();

        try (PreparedStatement statement = connectionUtils.getConnection().prepareStatement(FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                groups.add(extract(resultSet));
            }
            logger.info(format("found '%d' groups", groups.size()));
        } catch (SQLException e) {
            logger.error("Can't findByStudentsCountsLessEqual", e);
            throw new NoDBPropertiesException(e.getLocalizedMessage());
        }
        return groups;
    }

    private Group extract(ResultSet resultSet) throws SQLException {
        return new Group(
                resultSet.getInt("group_id"),
                resultSet.getString("group_name"));
    }

    private void requiredNonNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException(ExceptionsHandlingConstants.ARGUMENT_IS_NULL);
        }
    }

    @Override
    public Optional<Group> save(Group entity) {
        return Optional.empty();//ToDo::implement me
    }

    @Override
    public Optional<Group> findById(Integer integer) {
        return Optional.empty();//ToDo::implement me
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public long count() {
        return 0;//ToDo::implement me
    }

    @Override
    public void deleteById(Integer integer) {
//ToDo::implement me
    }

    @Override
    public void delete(Group entity) {
//ToDo::implement me
    }

    @Override
    public void deleteAll() {
//ToDo::implement me
    }
}
