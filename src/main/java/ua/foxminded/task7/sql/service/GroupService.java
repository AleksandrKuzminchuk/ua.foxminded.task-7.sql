package ua.foxminded.task7.sql.service;

import ua.foxminded.task7.sql.dao.CrudRepository;
import ua.foxminded.task7.sql.model.Group;

import java.util.List;

public interface GroupService extends CrudRepositoryService<Group, Integer> {

    void updateGroup(Group group);

    void saveAll(List<Group> groups);

    List<Group> findByStudentsCountsLessEqual(Integer count);
}
