package com.javarush.todoapp;

import com.javarush.todoapp.exceptions.DbLoudException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class LiquibaseConnect {

    public void loudDB() {

        HikariConfig hikariConfig = new HikariConfig(readProperties());
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor(getClass().getClassLoader());

        try (HikariDataSource dataSource = new HikariDataSource(hikariConfig);
             Connection connection = dataSource.getConnection()) {

            JdbcConnection jdbcConnection = new JdbcConnection(connection);
            Database db = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);

            Liquibase liquibase = new Liquibase("db/changelog/changelog.xml", resourceAccessor, db);
            liquibase.update();
        } catch (SQLException | DatabaseException e) {
            throw new DbLoudException("Db exception during loud db" + e);
        } catch (LiquibaseException e) {
            throw new DbLoudException("Liquibase exception during loud db" + e);
        }
    }

    private Properties readProperties() {
        Properties properties = new Properties();
        properties.put("driverClassName", System.getenv("DB_DRIVER"));
        properties.put("jdbcUrl", System.getenv("DB_URL_LOCAL"));
        properties.put("username", System.getenv("DB_USER"));
        properties.put("password", System.getenv("DB_PASSWORD"));

        return properties;
    }
}
