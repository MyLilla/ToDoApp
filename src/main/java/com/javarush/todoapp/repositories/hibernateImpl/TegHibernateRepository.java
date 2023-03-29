package com.javarush.todoapp.repositories.hibernateImpl;

import com.javarush.todoapp.model.Teg;
import com.javarush.todoapp.repositories.TegRepository;
import jakarta.persistence.NoResultException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;


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

    public Teg getByTitle(String title) {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery
                    ("from Teg t where t.title = '" + title + "'", Teg.class);
            Teg teg = (Teg) query.getSingleResult();
            session.getTransaction().commit();
            return teg;

        } catch (NoResultException exception) {
            return null;
        }
    }
}
