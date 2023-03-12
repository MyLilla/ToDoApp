package com.javarush.todoapp.repositories;

import com.javarush.todoapp.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class UserRepository extends GeneralRepository {

    private final Logger LOGGER = LogManager.getLogger(UserRepository.class);

    public UserRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
        LOGGER.info("Created UserRepository");
    }

    public void saveUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(user);
            session.getTransaction().commit();
        }
    }

    public User getWithPassword(String login, String password) {
        User result = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery(
                    "from User u where u.login = '" + login + "'", User.class
            );
            User user = (User) query.getSingleResult();
            if (user.getPassword().equals(password)) {
                result = user;
            }
            session.getTransaction().commit();
        }
        return result;
    }
}
