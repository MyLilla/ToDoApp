package com.javarush.todoapp.repositories;


import org.hibernate.SessionFactory;

public abstract class GeneralRepository<T> {

    public final SessionFactory sessionFactory;

    public GeneralRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
