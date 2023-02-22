package com.javarush.todoapp.servlets;

import com.javarush.todoapp.dto.TaskDto;
import com.javarush.todoapp.model.Task;
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

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        taskService = (TaskService) config.getServletContext().getAttribute("taskService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("url: /editTask, method: GET");

        String taskForEditId = request.getParameter("id");
        LOGGER.info("get id: {} for edition", taskForEditId);

        TaskDto taskDto = taskService.getTasksById(Long.parseLong(taskForEditId));

        request.setAttribute("task", taskDto);

        getServletContext().getRequestDispatcher("/editTask.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
