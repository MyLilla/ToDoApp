package com.javarush.todoapp.repositories;

import com.javarush.todoapp.model.Teg;
import com.javarush.todoapp.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class TegRepository extends GeneralRepository{
    public TegRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void save(Teg teg) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(teg);
            session.getTransaction().commit();
        }
    }

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
