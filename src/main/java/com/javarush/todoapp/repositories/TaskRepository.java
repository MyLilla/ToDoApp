package com.javarush.todoapp.repositories;

import com.javarush.todoapp.DbConfiguration;
import com.javarush.todoapp.model.Task;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class TaskRepository extends GeneralRepository {

    public TaskRepository(DbConfiguration dbConfiguration) {
        super(dbConfiguration);
    }


    public List<Task> getAll(Long id) {
        try (Session session = dbConfiguration.getSessionFactory().openSession()) {
            Query<Task> query = session.createQuery(
                    "FROM Task t WHERE userId = " + id, Task.class);
            return query.list();
        }
    }
    public List<Task> getAllWithPage(int pageNumber, int pageSize, Long id) {
        try (Session session = dbConfiguration.getSessionFactory().openSession()) {
            int offset = pageNumber * pageSize;
            Query<Task> query = session.createQuery(
                    "FROM Task t WHERE userId = " + id, Task.class);
            query.setFirstResult(offset);
            query.setMaxResults(pageSize);
            return query.list();
        }
    }

    public Task getById(Long id) {
        try (Session session = dbConfiguration.getSessionFactory().openSession()) {
            Query<Task> query = session.createQuery(
                    "FROM Task t WHERE t.id = " + id, Task.class);
            return query.getSingleResult();
        }
    }
}
