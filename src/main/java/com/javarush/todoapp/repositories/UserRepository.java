package com.javarush.todoapp.repositories;

import com.javarush.todoapp.DbConfiguration;
import com.javarush.todoapp.model.User;
import org.hibernate.Session;

public class UserRepository extends GeneralRepository{

    public UserRepository(DbConfiguration dbConfiguration) {
        super(dbConfiguration);
    }

    public void saveUser(User user){
        try (Session session = dbConfiguration.getSessionFactory().openSession()){
            session.beginTransaction();
            session.saveOrUpdate(user);
            session.getTransaction().commit();
        }

    }

    // обновить юзера
}
