package com.javarush.todoapp.repositories;

import com.javarush.todoapp.DbConfiguration;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public abstract class GeneralRepository<T> {

    public final DbConfiguration dbConfiguration;

    public GeneralRepository(DbConfiguration dbConfiguration) {
        this.dbConfiguration = dbConfiguration;
    }


}
