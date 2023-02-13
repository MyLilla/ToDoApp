package com.javarush.todoapp;

import com.javarush.todoapp.model.Comment;
import com.javarush.todoapp.model.Task;
import com.javarush.todoapp.model.Teg;
import com.javarush.todoapp.model.User;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class DbConfiguration {

    @Getter
    private final SessionFactory sessionFactory;

    public DbConfiguration() {
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "org.postgresql.Driver");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        properties.put(Environment.URL, "jdbc:postgresql://localhost:5432/postgres");
        properties.put(Environment.USER, "postgres");
        properties.put(Environment.PASS, "1234");

        properties.put(Environment.HBM2DDL_AUTO, "update");


        sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Comment.class)
                .addAnnotatedClass(Task.class)
                .addAnnotatedClass(Teg.class)
                .setProperties(properties)
                .buildSessionFactory();
    }
}
