package main.java.dao;

import main.java.model.Groups;

import java.util.List;

public interface GroupDao {

    void saveAll(List<Groups> groups);

    List<Groups> findByStudentsCountsLessEqual(Integer count);
}
