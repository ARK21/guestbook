package com.ark.store;

import com.ark.models.UserData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Collection;

/**
 * Class get access to database with hibernate.
 */
public class DatabaseManager implements Base {

    /**
     * Instance of  factory to get access
     */
    private final SessionFactory factory;

    /**
     * Constructor which initialize factory
     */
    public DatabaseManager() {
        factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    /**
     * Method gets list of all UserData from database
     * @return list of UserData
     */
    @Override
    public Collection<UserData> values() {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            return session.createQuery("from UserData").list();
        } finally {
            transaction.commit();
            session.close();
        }
    }

    /**
     * Saves UserData instance in database
     * @param userData instance of UserData
     * @return UserData id in database
     */
    @Override
    public int save(UserData userData) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(userData);
            return userData.getId();
        } finally {
            transaction.commit();
            session.close();
        }
    }

}
