package service;

import dao.CrudRepository;
import model.Group;

import java.util.List;

public interface GroupService extends CrudRepository<Group, Integer> {

    void updateGroup(Group group);

    void saveAll(List<Group> groups);

    List<Group> findByStudentsCountsLessEqual(Integer count);
}
