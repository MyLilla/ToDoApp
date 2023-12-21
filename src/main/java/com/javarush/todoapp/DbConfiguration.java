package com.javarush.todoapp;

import com.javarush.todoapp.model.Task;
import com.javarush.todoapp.model.Teg;
import com.javarush.todoapp.model.User;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class DbConfiguration {

    private final Logger LOGGER = LogManager.getLogger(DbConfiguration.class);
    @Getter
    private final SessionFactory sessionFactory;

    public DbConfiguration() {

        Properties properties = new Properties();

        properties.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres");
        properties.put("hibernate.connection.username", "postgres");
        properties.put("hibernate.connection.password", "1234");

        properties.put("hibernate.hbm2ddl", "validate");

        LOGGER.info("Added properties for data base: {}", properties);

        sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Task.class)
                .addAnnotatedClass(Teg.class)
                .setProperties(properties)
                .buildSessionFactory();

        LOGGER.info("Session factory is config. and connected");
    }
}
