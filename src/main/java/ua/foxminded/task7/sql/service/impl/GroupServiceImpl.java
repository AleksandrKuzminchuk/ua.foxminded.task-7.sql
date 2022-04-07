package ua.foxminded.task7.sql.service.impl;

import ua.foxminded.task7.sql.dao.GroupDao;
import ua.foxminded.task7.sql.exceptions.ExceptionsHandlingConstants;
import ua.foxminded.task7.sql.exceptions.NotFoundException;
import ua.foxminded.task7.sql.model.Group;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.log4j.Logger;
import ua.foxminded.task7.sql.service.GroupService;

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
    public Group save(Group entity) {
        requiredNonNull(entity);
        logger.info(format("saving %s...", entity));
        Group group = groupDao.save(entity).orElseThrow(() -> new NotFoundException(format("Can't save group %s",entity)));
        logger.info(format("SAVED group %s", entity));
        return group;
    }

    @Override
    public Group findById(Integer integer) {
        requiredNonNull(integer);
        logger.info(format("Find groups by Id('%d')", integer));
        Group group = groupDao.findById(integer).orElseThrow(() -> new NotFoundException(format("Can't find group by Id - %d", integer)));
        logger.info(format("Found groups %s by Id('%d')", group,integer));
        return group;
    }

    @Override
    public boolean existsById(Integer integer) {
        throw new NotImplementedException("Method 'existsById' not implemented");
    }

    @Override
    public List<Group> findAll() {
        logger.info("getAll()...");
        List<Group> groups = groupDao.findAll();
        if (groups == null){
            throw new NotFoundException("Can't find all groups");
        }
        logger.info(format("Found all groups %s",groups));
        return groups;
    }

    @Override
    public long count() {
        logger.info("find count groups...");
        long count = groupDao.count();
        logger.info(format("Found count groups %d", count));
        return count;
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
        logger.info(format("Find groups by students counts less equal('%d')", count));
        List<Group> groups = groupDao.findByStudentsCountsLessEqual(count);
        if (groups == null){
            throw new NotFoundException(format("Can't find groups by students counts less equal - %d",count));
        }
        logger.info(format("Found groups %s by students counts less equal - %d", groups,count));
        return groups;
    }

    private void requiredNonNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException(ExceptionsHandlingConstants.ARGUMENT_IS_NULL);
        }
    }
}


