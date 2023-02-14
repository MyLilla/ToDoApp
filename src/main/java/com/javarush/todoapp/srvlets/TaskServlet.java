package com.javarush.todoapp.srvlets;


import com.javarush.todoapp.dto.TaskDto;
import com.javarush.todoapp.model.Task;
import com.javarush.todoapp.model.User;
import com.javarush.todoapp.services.TaskService;

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

    private TaskService taskService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        taskService = (TaskService) config.getServletContext().getAttribute("taskService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        TaskDto taskDto = taskService.getTasksById(1L);

        // нужно прикрепить к ответу все задачи (юзер в сессии)

        request.setAttribute("tasks", taskDto);

        System.out.println(taskDto);

        getServletContext().getRequestDispatcher("/dashboard.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // для юзера из сессии
        // получить данные для задачи
        // создать и сохранить задачу
        // отправить на dashboard

        System.out.println("сроботал пост в таск сервлете");
    }
}
