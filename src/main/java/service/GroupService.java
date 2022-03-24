package main.java.service;

import main.java.dao.CrudRepository;
import main.java.model.Group;

import java.util.List;

public interface GroupService extends CrudRepository<Group, Integer> {

    void updateGroup(Group group);

    void saveAll(List<Group> groups);

    List<Group> findByStudentsCountsLessEqual(Integer count);
}
