package com.smirnov.springschooldatabase.dao.impl;

import com.smirnov.springschooldatabase.dao.CrudDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Optional;

@Component
public abstract class AbstractDao<E> implements CrudDao<Integer, E> {

    protected JdbcTemplate jdbcTemplate;
    protected TransactionTemplate transactionTemplate;
    protected final String findByIdQuery;
    protected final String findAllQuery;
    protected final String saveQuery;
    protected final String updateQuery;
    protected final String deleteByIdQuery;

    @Autowired
    protected AbstractDao(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate,
                          String findByIdQuery, String findAllQuery, String saveQuery,
                          String updateQuery, String deleteByIdQuery) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
        this.findByIdQuery = findByIdQuery;
        this.findAllQuery = findAllQuery;
        this.saveQuery = saveQuery;
        this.updateQuery = updateQuery;
        this.deleteByIdQuery = deleteByIdQuery;
    }

    @Override
    public Optional<E> findById(Integer id) {

        return Optional.ofNullable(findByIntParam(findByIdQuery, id));
    }

    @Override
    public List<E> findAll() {

        return findAllEntities(findAllQuery);
    }

    @Override
    public List<E> findAll(int leftScope, int rightScope) {

        return findAllEntitiesWithinScope(findAllQuery, leftScope, rightScope);
    }

    @Override
    public void save(E entity) {

        insert(findAllQuery, entity);
    }

    @Override
    public void update(Integer id, E entity) {

        updateValue(updateQuery, id, entity);
    }

    @Override
    public void deleteById(Integer id) {

        deleteValue(deleteByIdQuery, id);
    }

    protected abstract E findByIntParam(String sql, Integer id);

    protected abstract List<E> findAllEntities(String sql);

    protected abstract List<E> findAllEntitiesWithinScope(String sql, int leftScope, int rightScope);

    protected abstract void insert(String sql, E entity);

    protected abstract void updateValue(String sql, Integer id, E entity);

    protected abstract void deleteValue(String sql, Integer id);

}
