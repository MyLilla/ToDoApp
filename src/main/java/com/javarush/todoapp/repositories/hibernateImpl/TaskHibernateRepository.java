package com.javarush.todoapp.repositories.hibernateImpl;

import com.javarush.todoapp.model.Task;
import com.javarush.todoapp.model.Teg;
import com.javarush.todoapp.repositories.TaskRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Set;

public class TaskHibernateRepository extends GeneralHibernateRepository implements TaskRepository {

    private final Logger LOGGER = LogManager.getLogger(TaskHibernateRepository.class);

    public TaskHibernateRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    public List<Task> getAll(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Task> query = session.createQuery(
                    "from Task t where t.userId = " + id, Task.class);

            List<Task> taskList = query.list();
            session.getTransaction().commit();
            return taskList;
        }
    }

    @Override
    public List<Task> getAllWithLimit(Long id, Integer pageSize) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Task> query = session.createQuery(
                    "from Task t where t.userId = " + id, Task.class);
            query.setMaxResults(pageSize);
            List<Task> taskList = query.list();
            session.getTransaction().commit();
            return taskList;
        }
    }

    @Override
    public Long getCount(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String hql = "select count(*) from Task t where t.userId = " + id;
            Query<Long> query = session.createQuery(hql, Long.class);
            session.getTransaction().commit();
            return query.getSingleResult();
        }
    }

    @Override
    public Task getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Task.class, id);
        }
    }

    @Override
    public Long saveOrUpdate(Task task) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Task newTask = session.merge(task);
            session.getTransaction().commit();
            return newTask.getId();
        }
    }

    @Override
    public void deleteTask(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Task task = session.find(Task.class, id);
            LOGGER.info("Got task for delete: {}, with id: {}", task, task.getId());

            task.setTegs(null);
            task.setUserId(null);
            session.saveOrUpdate(task);

            session.remove(task);
            LOGGER.info("Task was removed");
            session.getTransaction().commit();
        }
    }

    @Override
    public void joinTegsInTask(Long task_id, Set<Teg> tegs) {
        try (Session session = sessionFactory.openSession()) {
            Task taskForAdding = session.find(Task.class, task_id);
            for (Teg t : tegs) {
                Teg teg = session.find(Teg.class, t.getId());
//               taskForAdding.getTegs().add(teg);
//                System.out.println(teg);
            }
            session.update(taskForAdding);
        }
    }
}
