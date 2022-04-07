package ua.foxminded.task7.sql.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CrudRepositoryService<T, ID extends Serializable> {
    T save(T entity);
    T findById(ID id) throws Exception;
    boolean existsById(ID id);
    List<T> findAll();
    long count();
    void deleteById(ID id);
    void delete(T entity);
    void deleteAll();
}
