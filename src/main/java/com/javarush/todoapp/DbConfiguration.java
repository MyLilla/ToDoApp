package com.javarush.todoapp;

import com.javarush.todoapp.model.Comment;
import com.javarush.todoapp.model.Task;
import com.javarush.todoapp.model.Teg;
import com.javarush.todoapp.model.User;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class DbConfiguration {

    private final Logger LOGGER = LogManager.getLogger(DbConfiguration.class);
    @Getter
    private final SessionFactory sessionFactory;

    public DbConfiguration() {
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        properties.put(Environment.URL, "jdbc:p6spy:postgresql://localhost:5432/postgres");
        properties.put(Environment.USER, "postgres");
        properties.put(Environment.PASS, "1234");

        properties.put(Environment.HBM2DDL_AUTO, "validate");


        LOGGER.info("Added properties for data base: {}", properties);


        sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Task.class)
                .addAnnotatedClass(Teg.class)
                .addAnnotatedClass(Comment.class)
                .setProperties(properties)
                .buildSessionFactory();

        LOGGER.info("Session factory is config. and connected");
    }
}
