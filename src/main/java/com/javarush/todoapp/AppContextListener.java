package com.javarush.todoapp;

import com.javarush.todoapp.repositories.TaskRepository;
import com.javarush.todoapp.repositories.TegRepository;
import com.javarush.todoapp.repositories.UserRepository;
import com.javarush.todoapp.repositories.hibernateImpl.TaskHibernateRepository;
import com.javarush.todoapp.repositories.hibernateImpl.TegHibernateRepository;
import com.javarush.todoapp.repositories.hibernateImpl.UserHibernateRepository;
import com.javarush.todoapp.services.TaskService;
import com.javarush.todoapp.services.TegService;
import com.javarush.todoapp.services.UserService;
import org.hibernate.SessionFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();

        LiquibaseConnect liquibaseConnect = new LiquibaseConnect();
        liquibaseConnect.loudDB();

        DbConfiguration dbConfiguration = new DbConfiguration();
        SessionFactory sessionFactory = dbConfiguration.getSessionFactory();

        UserRepository userRepository = new UserHibernateRepository(sessionFactory);
        TaskRepository taskRepository = new TaskHibernateRepository(sessionFactory);
        TegRepository tegRepository = new TegHibernateRepository(sessionFactory);

        UserService userService = new UserService(userRepository);
        TaskService taskService = new TaskService(taskRepository, userRepository, tegRepository);
        TegService tegService = new TegService(tegRepository, userRepository);

        context.setAttribute("userService", userService);
        context.setAttribute("taskService", taskService);
        context.setAttribute("tegService", tegService);
    }
}
