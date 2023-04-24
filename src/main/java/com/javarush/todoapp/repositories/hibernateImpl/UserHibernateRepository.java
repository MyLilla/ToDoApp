package com.javarush.todoapp.repositories.hibernateImpl;

import com.javarush.todoapp.model.User;
import com.javarush.todoapp.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class UserHibernateRepository extends GeneralHibernateRepository implements UserRepository {

    private final Logger LOGGER = LogManager.getLogger(UserHibernateRepository.class);

    public UserHibernateRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
        LOGGER.info("Created UserRepository");
    }

    @Override
    public void saveUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(user);
            LOGGER.debug("Save user: {} to db", user.getUserName());
            session.getTransaction().commit();
        }
    }

    @Override
    public User getWithPassword(String login, String password) {
        User result = null;
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery
                    ("from User u where u.login = '" + login + "'", User.class);
            User user = (User) query.getSingleResult();
            LOGGER.debug("Get user: {}, with id: {}", user, user.getId());

            if (user.getPassword().equals(password)) {
                result = user;
            }
        }
        return result;
    }

    @Override
    public User getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            LOGGER.debug("Got user id: {}", id);
            return session.get(User.class, id);
        }
    }
}
