package com.javarush.todoapp.repositories.hibernateImpl;

import com.javarush.todoapp.model.Teg;
import com.javarush.todoapp.model.User;
import com.javarush.todoapp.repositories.TegRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class TegHibernateRepository extends GeneralHibernateRepository implements TegRepository {

    private final Logger LOGGER = LogManager.getLogger(TegHibernateRepository.class);
    public TegHibernateRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void save(Teg teg) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(teg);
            LOGGER.info("Teg saved in db");
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Teg> getAll(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            List<Teg> tegList = user.getTegs().stream().toList();
            session.getTransaction().commit();
            return tegList;
        }
    }
}
