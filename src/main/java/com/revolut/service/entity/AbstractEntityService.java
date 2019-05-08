package com.revolut.service.entity;

import com.revolut.DB;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.Optional;

public abstract class AbstractEntityService<T> {

    private final Class<T> entityClass;

    protected AbstractEntityService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public Optional<T> findOne(Serializable id) {
        Session session = DB.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();

        T entity = session.get(this.entityClass, id);

        session.getTransaction().commit();
        return Optional.ofNullable(entity);
    }

    public T save(T entity) {
        Session session = DB.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();

        session.save(entity);

        session.getTransaction().commit();
        return entity;
    }

}
