package com.revolut.service.entity;

import com.revolut.DB;
import com.revolut.model.entities.AccountEntity;
import org.hibernate.Session;

import java.util.Collections;
import java.util.List;

public class AccountEntityService extends AbstractEntityService<AccountEntity> {

    public AccountEntityService() {
        super(AccountEntity.class);
    }

    public List<AccountEntity> findAll() {
        Session session = DB.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();

        try {

            return session.createQuery("select a from AccountEntity a", AccountEntity.class).getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            session.getTransaction().commit();
        }
    }
}
