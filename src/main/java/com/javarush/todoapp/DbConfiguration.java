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

        properties.put("hibernate.connection.driver_class", System.getenv("DB_DRIVER"));
        properties.put("hibernate.dialect", System.getenv("DB_DIALECT"));
        properties.put("hibernate.connection.url", System.getenv("DB_URL"));
        properties.put("hibernate.connection.username", System.getenv("DB_USER"));
        properties.put("hibernate.connection.password", System.getenv("DB_PASSWORD"));

        properties.put("hibernate.hbm2ddl", System.getenv("DB_HBM2DDL_AUTO"));

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
