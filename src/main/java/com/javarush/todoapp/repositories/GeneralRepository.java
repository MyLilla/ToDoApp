package com.javarush.todoapp.repositories;

import com.javarush.todoapp.DbConfiguration;

public abstract class GeneralRepository<T> {

    public final DbConfiguration dbConfiguration;

    public GeneralRepository(DbConfiguration dbConfiguration) {
        this.dbConfiguration = dbConfiguration;
    }
}
