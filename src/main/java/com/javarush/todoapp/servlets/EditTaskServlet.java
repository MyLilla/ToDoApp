package com.javarush.todoapp.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.javarush.todoapp.dto.TaskDto;
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

@WebServlet(name = "EditTaskServlet", value = "/editTask")
public class EditTaskServlet extends HttpServlet {

    private final Logger LOGGER = LogManager.getLogger(EditTaskServlet.class);
    private TaskService taskService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        taskService = (TaskService) config.getServletContext().getAttribute("taskService");
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String taskForEditId = request.getParameter("taskForEditId");
        request.getSession().setAttribute("taskForEditId", taskForEditId);

        LOGGER.info("get id: {} for edition", taskForEditId);

        TaskDto taskDto = taskService.getTasksDtoById(Long.parseLong(taskForEditId));
        LOGGER.info("get taskDto {} by id {}", taskDto, taskForEditId);
        request.getSession().setAttribute("task", taskDto);

        getServletContext().getRequestDispatcher("/editTask.html").forward(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        TaskDto taskDto = (TaskDto) request.getSession().getAttribute("task");
        LOGGER.info("TaskDto: {}", taskDto);

        String tasksJson = objectMapper.writeValueAsString(taskDto);
        LOGGER.info("from obj to String JSON: {}", tasksJson);

        response.setContentType("application/json");
        response.getWriter().write(tasksJson);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long taskId = Long.parseLong((String) request.getSession().getAttribute("taskForEditId"));
        LOGGER.info("task id for edit: {}", taskId);

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String hours = request.getParameter("hours");
        String[] tegs = request.getParameterValues("tegs[]");
        String status = request.getParameter("status");
        String priority = request.getParameter("priority");

        taskService.updateTask(title, description, hours, tegs,
                status, priority, taskId);

        getServletContext().getRequestDispatcher("/dashboard.html").forward(request, response);
    }
}
