package main.java.dao;

import main.java.model.Group;

import java.util.List;

public interface GroupDao extends CrudRepository<Group, Integer> {

    void saveAll(List<Group> groups);

    List<Group> findByStudentsCountsLessEqual(Integer count);

}
