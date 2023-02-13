package com.javarush.todoapp.srvlets;


import com.javarush.todoapp.DbConfiguration;
import com.javarush.todoapp.model.User;
import com.javarush.todoapp.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserServlet", value = "/user")
public class UserServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        userService = (UserService) context.getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String login = request.getParameter("login");
       request.setAttribute("login", login);
        System.out.println("login: " + login);

        getServletContext().getRequestDispatcher("/dashboard.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");

        User user = userService.createUser(name);

        System.out.println(name);

        getServletContext().getRequestDispatcher("/dashboard.html").forward(request, response);
    }
}
