package com.smirnov.springschooldatabase.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<Id, E> {

    Optional<E> findById(Id id);

    List<E> findAll();

    List<E> findAll(int leftScope, int rightScope);

    void save(E e);

    void update(Id id, E e);

    void deleteById(Id id);

}
