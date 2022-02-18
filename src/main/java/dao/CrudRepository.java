package main.java.dao;

import java.util.List;
import java.util.Optional;

public interface CrudRepository <T, ID>{
        Optional<T> save(T entity);
        Optional<T> findById(ID id);
        boolean existsById(ID id);
        List<T> findAll();
        long count();
        void deleteById(ID id);
        void delete(T entity);
        void deleteAll();
}
