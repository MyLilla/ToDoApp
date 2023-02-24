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
            session.beginTransaction();
            Query<Task> query = session.createQuery(
                    "from Task t where t.userId = " + id, Task.class);

            List<Task> taskList = query.list();
            session.getTransaction().commit();
            return taskList;
        }
    }

    public List<Task> getAllWithLimit(Long id, Integer pageSize) {
        try (Session session = dbConfiguration.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query<Task> query = session.createQuery(
                    "from Task t where t.userId = " + id, Task.class);
            query.setMaxResults(pageSize);
            List<Task> taskList = query.list();
            session.getTransaction().commit();
            return taskList;
        }
    }

    public Long getCount(Long id) {
        try (Session session = dbConfiguration.getSessionFactory().openSession()) {
            session.beginTransaction();
            String hql = "select count(*) from Task t where t.userId = " + id;
            Query<Long> query = session.createQuery(hql, Long.class);
            session.getTransaction().commit();
            return query.getSingleResult();
        }
    }

    public Task getById(Long id) {
        try (Session session = dbConfiguration.getSessionFactory().openSession()) {
            return session.get(Task.class, id);
        }
    }

    public void saveOrUpdate(Task task) {
        try (Session session = dbConfiguration.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.merge(task);
            session.getTransaction().commit();
        }
    }

    public void deleteTask(Long id) {
        try (Session session = dbConfiguration.getSessionFactory().openSession()) {
            session.beginTransaction();
            Task task = session.find(Task.class, id);
            session.remove(task);
            session.getTransaction().commit();
        }
    }
}
