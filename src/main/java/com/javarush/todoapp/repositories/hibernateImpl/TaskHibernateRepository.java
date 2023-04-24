package com.javarush.todoapp.repositories.hibernateImpl;

import com.javarush.todoapp.model.Task;
import com.javarush.todoapp.repositories.TaskRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class TaskHibernateRepository extends GeneralHibernateRepository implements TaskRepository {

    private final Logger LOGGER = LogManager.getLogger(TaskHibernateRepository.class);

    public TaskHibernateRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Task> getAllWithLimit(Long userId, Integer pageSize) {
        try (Session session = sessionFactory.openSession()) {
            Query<Task> query = session.createQuery(
                    "from Task t where t.userId = " + userId, Task.class);
            query.setMaxResults(pageSize);
            LOGGER.debug("Tasks were got with limit: {}", pageSize);
            return query.list();
        }
    }

    @Override
    public Long getCount(Long userId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "select count(*) from Task t where t.userId = " + userId;
            Query<Long> query = session.createQuery(hql, Long.class);
            LOGGER.debug("Count was got for user: {}", userId);
            return query.getSingleResult();
        }
    }

    @Override
    public Task getById(Long taskId) {
        try (Session session = sessionFactory.openSession()) {
            LOGGER.debug("Got task with id: {}", taskId);
            return session.get(Task.class, taskId);
        }
    }

    @Override
    public Long saveOrUpdate(Task task) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Task newTask = session.merge(task);
            session.getTransaction().commit();
            LOGGER.debug("Save or update task: {} with id: {}", task, task.getId());
            return newTask.getId();
        }
    }

    @Override
    public void deleteTask(Long taskId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Task task = session.find(Task.class, taskId);
            LOGGER.info("Got task for delete: {}, with id: {}", task, task.getId());

            session.remove(task);
            LOGGER.debug("Task was removed");
            session.getTransaction().commit();
        }
    }
}
