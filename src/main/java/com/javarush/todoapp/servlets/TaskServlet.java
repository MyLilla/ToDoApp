package com.javarush.todoapp.servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.todoapp.dto.TaskDto;
import com.javarush.todoapp.dto.UserDto;
import com.javarush.todoapp.model.Teg;
import com.javarush.todoapp.model.User;
import com.javarush.todoapp.services.TaskService;
import com.javarush.todoapp.services.TegService;
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
import java.util.Set;

@WebServlet(name = "TaskServlet", value = "/task")
public class TaskServlet extends HttpServlet {

    private final Logger LOGGER = LogManager.getLogger(TaskServlet.class);
    private TaskService taskService;
    private TegService tegService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        taskService = (TaskService) config.getServletContext().getAttribute("taskService");
        tegService = (TegService) config.getServletContext().getAttribute("tegService");
        objectMapper = new ObjectMapper();
        LOGGER.info("create object Mapper");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("url: /task, method: GET, get task list");

        Long userId = (Long) request.getSession().getAttribute("userId");

        String countTasks = request.getParameter("countTasks");
        List<TaskDto> taskList = taskService.getAllTasks(userId, countTasks);
        LOGGER.info("TasksList was get from db {}", taskList);

        String tasksJson = objectMapper.writeValueAsString(taskList);
        LOGGER.info("from obj to String JSON: {}", tasksJson);

        response.setContentType("application/json");
        response.getWriter().write(tasksJson);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("url: /task, method: POST, creating new task");

        Long userId = (Long) request.getSession().getAttribute("userId");

        String tegs = request.getParameter("tegs");
        LOGGER.info("Get tegs: {}", tegs);

        Set<Teg> addedTegs = tegService.getSetTegs(tegs, userId);

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String hours = request.getParameter("hours");
        String status = request.getParameter("status");
        String priority = request.getParameter("priority");

        taskService.createTask(title, description, hours, addedTegs,
                status, priority, userId);

        getServletContext().getRequestDispatcher("/dashboard.html").forward(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("url: /task, method: DELETE");

        String id = request.getParameter("id");
        LOGGER.info("id for delete: {}", id);

        taskService.deleteTask(id);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("url: task, method: PUT, get count tasks");

        User user = (User) request.getSession().getAttribute("user");

        Long count = taskService.getCountAllTasks(user);
        LOGGER.info("for User with id: {} count tasks: {}", user.getId(), count);

        response.getWriter().write(count.intValue());
    }
}
