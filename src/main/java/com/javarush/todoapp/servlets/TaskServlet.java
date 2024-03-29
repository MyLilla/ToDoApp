package com.javarush.todoapp.servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.javarush.todoapp.dto.TaskDto;
import com.javarush.todoapp.model.User;
import com.javarush.todoapp.services.TaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "TaskServlet", value = "/task")
public class TaskServlet extends HttpServlet {

    private final Logger LOGGER = LogManager.getLogger(TaskServlet.class);
    private TaskService taskService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        taskService = (TaskService) config.getServletContext().getAttribute("taskService");
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        LOGGER.info("create object Mapper");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long userId = (Long) request.getSession().getAttribute("userId");

        String countTasks = request.getParameter("countTasks");
        List<TaskDto> taskList = taskService.getAllTasksDto(userId, countTasks);
        LOGGER.info("TasksList was get from db {}", taskList);

        String tasksJson = objectMapper.writeValueAsString(taskList);
        LOGGER.info("from obj to String JSON: {}", tasksJson);

        response.setContentType("application/json");
        response.getWriter().write(tasksJson);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long userId = (Long) request.getSession().getAttribute("userId");

        String[] tegs = request.getParameterValues("tegs[]");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String hours = request.getParameter("hours");
        String status = request.getParameter("status");
        String priority = request.getParameter("priority");

        taskService.createTask(title, description, hours, tegs,
                status, priority, userId);


        getServletContext().getRequestDispatcher("/dashboard.html").forward(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");
        LOGGER.info("id for delete: {}", id);

        taskService.deleteTask(id);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        Long count = taskService.getCountAllTasks(user);
        LOGGER.info("for User with id: {} count tasks: {}", user.getId(), count);

        response.getWriter().write(count.intValue());
    }
}
