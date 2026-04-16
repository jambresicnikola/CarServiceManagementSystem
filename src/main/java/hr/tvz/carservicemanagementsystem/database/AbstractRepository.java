package hr.tvz.carservicemanagementsystem.database;

import hr.tvz.carservicemanagementsystem.model.Entity;

import java.util.List;

abstract class AbstractRepository<T extends Entity> {
    abstract T findById(Long id);
    abstract List<T> findAll();
    abstract void save(T entity);
    abstract void delete(T entity);
    abstract void update(T entity);
}
