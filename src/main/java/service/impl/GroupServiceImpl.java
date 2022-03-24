package main.java.service.impl;

import main.java.dao.GroupDao;
import main.java.exceptions.ExceptionsHandlingConstants;
import main.java.model.Group;
import main.java.service.GroupService;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

public class GroupServiceImpl implements GroupService {

    private static final Logger logger = Logger.getLogger(GroupServiceImpl.class);

    private final GroupDao groupDao;

    public GroupServiceImpl(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    @Override
    public Optional<Group> save(Group entity) {
        requiredNonNull(entity);
        logger.info(format("saving %s...", entity));
        logger.info(format("%s SAVED", entity));
        return groupDao.save(entity);
    }

    @Override
    public Optional<Group> findById(Integer integer) {
        requiredNonNull(integer);
        logger.info(format("findById('%d')", integer));
        return groupDao.findById(integer);
    }

    @Override
    public boolean existsById(Integer integer) {
        throw new NotImplementedException("Method 'existsById' not implemented");
    }

    @Override
    public List<Group> findAll() {
        logger.info("getAll()...");
        return groupDao.findAll();
    }

    @Override
    public long count() {
        logger.info("find count groups...");
        return groupDao.count();
    }

    @Override
    public void deleteById(Integer integer) {
        requiredNonNull(integer);
        logger.info(format("deleteById ('%d') group", integer));
        groupDao.deleteById(integer);
        logger.info(format("group with id '%d' DELETED", integer));
    }

    @Override
    public void delete(Group entity) {
        requiredNonNull(entity);
        logger.info(format("delete group by name '%s'", entity.getGroupName()));
        groupDao.delete(entity);
        logger.info(format("deleted group by name '%s'", entity.getGroupName()));
    }

    @Override
    public void deleteAll() {
        logger.info("delete all groups...");
        groupDao.deleteAll();
        logger.info("DELETED ALL groups");
    }

    @Override
    public void updateGroup(Group group) {
        requiredNonNull(group);
        logger.info(format("UPDATE group by ID - %d", group.getGroupId()));
        groupDao.updateGroup(group);
        logger.info(format("UPDATED %s SUCCESSFULLY", group));
    }

    @Override
    public void saveAll(List<Group> groups) {
        requiredNonNull(groups);
        logger.info(format("saving %d groups...", groups.size()));
        groupDao.saveAll(groups);
        logger.info(format("All %d groups saved SUCCESSFULLY", groups.size()));
    }

    @Override
    public List<Group> findByStudentsCountsLessEqual(Integer count) {
        requiredNonNull(count);
        logger.info(format("findByStudentsCountsLessEqual('%d')", count));
        return groupDao.findByStudentsCountsLessEqual(count);
    }

    private void requiredNonNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException(ExceptionsHandlingConstants.ARGUMENT_IS_NULL);
        }
    }
}


