package com.tsystems.ecm.dao;

import java.util.List;
import java.util.Optional;

//Not used yet
public interface Dao<T> {

    Optional<T> get(int id);

    List<T> getAll();

    void persist(T t);

    void update(T t, String[] params);

    void delete(T t);
}
