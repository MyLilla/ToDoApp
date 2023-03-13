package com.javarush.todoapp.servlets;

import com.javarush.todoapp.dto.UserDto;
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

import static java.util.Objects.isNull;

@WebServlet(name = "UserServlet", value = "/user")
public class UserServlet extends HttpServlet {

    private final Logger LOGGER = LogManager.getLogger(UserServlet.class);
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        userService = (UserService) context.getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("url: /user, method: POST, sign un user");

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = userService.getUserWithPassword(login, password);
        LOGGER.debug("User {} was got with login: {}", user, login);
        if (isNull(user)){
            getServletContext().getRequestDispatcher("/index.html").forward(request, response);
        }

        request.getSession().setAttribute("userId", user.getId());
        getServletContext().getRequestDispatcher("/dashboard.html").forward(request, response);
    }
}
