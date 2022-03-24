package main.java.dao.impl;

import main.java.dao.GroupDao;
import main.java.dao.constants.QueryConstantsGroups;
import main.java.exceptions.ExceptionsHandlingConstants;
import main.java.exceptions.NoDBPropertiesException;
import main.java.model.Group;
import main.java.util.ConnectionUtils;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.log4j.Logger;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

public class GroupDaoImpl implements GroupDao {

    private static final Logger logger = Logger.getLogger(GroupDaoImpl.class);

    private final ConnectionUtils connectionUtils;

    public GroupDaoImpl(ConnectionUtils connectionUtils) {
        this.connectionUtils = connectionUtils;
    }

    @Override
    public void saveAll(List<Group> groups) {
        requiredNonNull(groups);
        logger.info(format("saving %d groups...", groups.size()));
        groups.forEach(this::save);
        logger.info(format("All %d groups saved SUCCESSFULLY", groups.size()));
    }

    @Override
    public List<Group> findByStudentsCountsLessEqual(Integer count) {
        requiredNonNull(count);
        logger.info(format("findByStudentsCountsLessEqual('%d')", count));
        List<Group> groups = new LinkedList<>();

        try (PreparedStatement statement = connectionUtils.getConnection()
                .prepareStatement(QueryConstantsGroups.FIND_BY_STUDENTS_COUNTS_LESS_EQUAL)) {
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
        try (PreparedStatement statement = connectionUtils.getConnection().prepareStatement(QueryConstantsGroups.FIND_ALL)) {
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

    @Override
    public Optional<Group> save(Group entity) {
        requiredNonNull(entity);
        logger.info(format("saving %s...", entity));
        try (PreparedStatement statement = connectionUtils.getConnection()
                .prepareStatement(QueryConstantsGroups.SAVE_GROUP, new String[]{"group_id"})) {
            statement.setString(1, entity.getGroupName());
            statement.executeUpdate();
            ResultSet generatedKey = statement.getGeneratedKeys();
            generatedKey.next();
            Integer groupId = generatedKey.getInt("group_id");
            entity.setGroupId(groupId);
            Optional<Group> result = Optional.of(entity);
            logger.info(format("%s SAVED", entity));
            return result;
        } catch (SQLException e) {
            logger.error("Can't save group", e);
            throw new NoDBPropertiesException(e.getLocalizedMessage());
        }
    }

    @Override
    public Optional<Group> findById(Integer groupId) {
        requiredNonNull(groupId);
        logger.info(format("findById('%d')", groupId));
        try (PreparedStatement statement = connectionUtils.getConnection()
                .prepareStatement(QueryConstantsGroups.FIND_BY_GROUP_ID)) {
            statement.setInt(1, groupId);
            ResultSet resultSet = statement.executeQuery();
            Group result = resultSet.next() ? extract(resultSet) : null;
            logger.info(format("%s FOUND", result));
            return Optional.ofNullable(result);
        } catch (SQLException e) {
            logger.error("Can't find group by Id", e);
            throw new NoDBPropertiesException(e.getLocalizedMessage());
        }
    }

    @Override
    public boolean existsById(Integer integer) {
        throw new NotImplementedException("Method 'existsById' not implemented");
    }

    @Override
    public long count() {
        logger.info("find count groups...");
        long result = 0;
        try (PreparedStatement statement = connectionUtils.getConnection()
                .prepareStatement(QueryConstantsGroups.COUNT_GROUPS)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getLong("count");
            }
            logger.info(format("FOUND COUNT (%d) groups", result));
            return result;
        } catch (SQLException e) {
            logger.error("Can't find count groups", e);
            throw new NotImplementedException(e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteById(Integer groupId) {
        requiredNonNull(groupId);
        logger.info(format("deleteById ('%d') group", groupId));
        try (PreparedStatement statement = connectionUtils.getConnection().prepareStatement(
                QueryConstantsGroups.DELETE_BY_ID_GROUP)
        ) {
            statement.setInt(1, groupId);
            statement.executeUpdate();
            logger.info(format("group with id '%d' DELETED", groupId));
        } catch (SQLException e) {
            logger.error("Can't delete group by id '%d'", e);
            throw new NoDBPropertiesException(e.getLocalizedMessage());
        }
    }

    @Override
    public void delete(Group groupName) {
        requiredNonNull(groupName);
        logger.info(format("delete group by name '%s'", groupName.getGroupName()));
        try (PreparedStatement statement = connectionUtils.getConnection().prepareStatement(
                QueryConstantsGroups.DELETE_GROUPS)
        ) {
            statement.setString(1, groupName.getGroupName());
            statement.executeUpdate();
            logger.info(format("deleted group by name '%s'", groupName.getGroupName()));
        } catch (SQLException e) {
            logger.error("Can't delete group by name", e);
            throw new NoDBPropertiesException(e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteAll() {
        logger.info("delete all groups...");
        try (PreparedStatement statement = connectionUtils.getConnection().prepareStatement(
                QueryConstantsGroups.DELETE_ALL_GROUPS)
        ) {
            statement.executeUpdate();
            logger.info("DELETED ALL groups");
        } catch (SQLException e) {
            logger.error("Can't delete all groups");
            throw new NoDBPropertiesException(e.getLocalizedMessage());
        }
    }

    @Override
    public void updateGroup(Group group) {
        requiredNonNull(group);
        logger.info(format("UPDATE group by ID - %d", group.getGroupId()));
        try(PreparedStatement statement = connectionUtils.getConnection().prepareStatement(
                QueryConstantsGroups.UPDATE_GROUP)
                ){
            statement.setString(1, group.getGroupName());
            statement.setInt(2, group.getGroupId());
            statement.executeUpdate();
            logger.info(format("UPDATED %s SUCCESSFULLY", group));
        }catch (SQLException e){
            logger.error("Can't UPDATE group", e);
            throw new NoDBPropertiesException(e.getLocalizedMessage());
        }
    }

    private void requiredNonNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException(ExceptionsHandlingConstants.ARGUMENT_IS_NULL);
        }
    }
}
