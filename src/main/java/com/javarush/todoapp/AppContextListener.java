package com.javarush.todoapp;

import com.javarush.todoapp.repositories.TaskRepository;
import com.javarush.todoapp.repositories.TegRepository;
import com.javarush.todoapp.repositories.UserRepository;
import com.javarush.todoapp.services.TaskService;
import com.javarush.todoapp.services.TegService;
import com.javarush.todoapp.services.UserService;
import org.hibernate.Session;
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

        UserRepository userRepository = new UserRepository(sessionFactory);
        TaskRepository taskRepository = new TaskRepository(sessionFactory);
        TegRepository tegRepository = new TegRepository(sessionFactory);

        UserService userService = new UserService(userRepository);
        TaskService taskService = new TaskService(taskRepository);
        TegService tegService = new TegService(tegRepository);

        context.setAttribute("userService", userService);
        context.setAttribute("taskService", taskService);
        context.setAttribute("tegService", tegService);
    }
}
