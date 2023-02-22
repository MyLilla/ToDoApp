package com.javarush.todoapp.servlets;

import com.javarush.todoapp.model.User;
import com.javarush.todoapp.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "NewUserServlet", value = "/newUser")
public class NewUserServlet extends HttpServlet {

    private final Logger LOGGER = LogManager.getLogger(UserServlet.class);
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        userService = (UserService) context.getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        LOGGER.info("сработал GET  в новом юзере");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOGGER.info("url: /newUser, method: GET");
        // сделать dto
        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = userService.createUser(name, login, password);
        request.getSession().setAttribute("user", user);

        getServletContext().getRequestDispatcher("/dashboard.html").forward(request, response);
    }
}