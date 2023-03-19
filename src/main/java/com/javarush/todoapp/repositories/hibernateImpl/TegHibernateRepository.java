package com.javarush.todoapp.repositories.hibernateImpl;

import com.javarush.todoapp.model.Task;
import com.javarush.todoapp.model.Teg;
import com.javarush.todoapp.model.User;
import com.javarush.todoapp.repositories.TegRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public void joinTegsInTask(Task taskForAdding, Set<Teg> tegs) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Task task = session.find(Task.class, taskForAdding.getId());
            for (Teg teg : tegs) {
                session.find(Teg.class, teg.getId());
                task.getTegs().add(teg);
            }
            session.update(task);
            session.getTransaction().commit();
        }
    }

    public Set<Teg> getByTitle(String title) {

        Set<Teg> result = new HashSet<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery
                    ("from Teg t where t.title = '" + title + "'", Teg.class);
            Teg teg = (Teg) query.getSingleResult();
            result.add(teg);
            session.getTransaction().commit();
        }
        return result;
    }
}
