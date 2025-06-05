package org.example.dao;

import java.util.List;

public interface ElectronicDeviceDAO<T> {
    void save(T entity);
    T findById(int id);
    List<T> findAll();
    void update(T entity);
    void delete(T entity);
}
