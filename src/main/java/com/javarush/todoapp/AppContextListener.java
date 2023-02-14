package com.javarush.todoapp;

import com.javarush.todoapp.repositories.TaskRepository;
import com.javarush.todoapp.repositories.UserRepository;
import com.javarush.todoapp.services.TaskService;
import com.javarush.todoapp.services.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();

        DbConfiguration dbConfiguration = new DbConfiguration();
        UserRepository userRepository = new UserRepository(dbConfiguration);
        TaskRepository taskRepository = new TaskRepository(dbConfiguration);

        UserService userService = new UserService(userRepository);
        TaskService taskService = new TaskService(taskRepository);

        context.setAttribute("userService", userService);
        context.setAttribute("taskService", taskService);
    }
}
