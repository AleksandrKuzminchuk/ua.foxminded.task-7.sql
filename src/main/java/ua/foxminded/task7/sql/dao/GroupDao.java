package ua.foxminded.task7.sql.dao;

import ua.foxminded.task7.sql.model.Group;

import java.util.List;

public interface GroupDao extends CrudRepository<Group, Integer> {

    void updateGroup(Group group);

    void saveAll(List<Group> groups);

    List<Group> findByStudentsCountsLessEqual(Integer count);

}
