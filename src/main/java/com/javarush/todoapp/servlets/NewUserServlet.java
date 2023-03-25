package com.javarush.todoapp.servlets;

import com.javarush.todoapp.exceptions.LongNameException;
import com.javarush.todoapp.model.User;
import com.javarush.todoapp.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;

@WebServlet(name = "NewUserServlet", value = "/newUser")
public class NewUserServlet extends HttpServlet {

    private final Logger LOGGER = LogManager.getLogger(NewUserServlet.class);
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        userService = (UserService) context.getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("url: /newUser, method: post, log in new user");

        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user;
        try {
            user = userService.createUser(name, login, password);
        } catch (LoginException e) {
            response.sendRedirect("index.html?error=This login already exist, use something new");
            LOGGER.debug("incorrect login. redirect to index.html");
            return;
        } catch (LongNameException e) {
            response.sendRedirect("index.html?error=This User name is very long, use something short");
            LOGGER.debug("long user_name. redirect to index.html");
            return;
        }
        if (isNull(user)) {
            response.sendRedirect("index.html?error=You added incorrect information, try again");
            LOGGER.debug("incorrect information. redirect to index.html");
            return;
        }
        LOGGER.info("Created new user: {}, name: {}, login: {}",
                user, user.getUserName(), user.getLogin());

        request.getSession().setAttribute("userId", user.getId());

        getServletContext().getRequestDispatcher("/dashboard.html").forward(request, response);
    }
}
