package com.javarush.todoapp.repositories.hibernateImpl;


import org.hibernate.SessionFactory;

public abstract class GeneralHibernateRepository<T> {

    public final SessionFactory sessionFactory;
    public GeneralHibernateRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
