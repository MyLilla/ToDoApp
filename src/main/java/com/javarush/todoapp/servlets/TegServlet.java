package com.javarush.todoapp.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.javarush.todoapp.dto.TegDto;
import com.javarush.todoapp.services.TegService;
import com.javarush.todoapp.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "TegServlet", value = "/teg")
public class TegServlet extends HttpServlet {

    private final Logger LOGGER = LogManager.getLogger(TegServlet.class);

    private TegService tegService;
    private UserService userService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        tegService = (TegService) config.getServletContext().getAttribute("tegService");
        userService = (UserService) config.getServletContext().getAttribute("userService");
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        LOGGER.info("create object Mapper");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long userId = (Long) request.getSession().getAttribute("userId");


        List<TegDto> tegDtoList = tegService.getAllUsersTegs(userId);

        LOGGER.info("tegDtoList was get from db {}", tegDtoList);

        String tegsJson = objectMapper.writeValueAsString(tegDtoList);
        LOGGER.info("from obj to String JSON: {}", tegsJson);

        response.setContentType("application/json");
        response.getWriter().write(tegsJson);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long userId = (Long) request.getSession().getAttribute("userId");

        String teg = request.getParameter("teg");
        String color = request.getParameter("color");
        LOGGER.info("Get teg: {} and color: {} for creating", teg, color);

        tegService.createTeg(teg, color, userId);

        try {
            userService.updateUser(userId);
        } catch (LoginException e) {
            LOGGER.error("Changed login for user during adding teg. userID: {}", userId);
        }

        getServletContext().getRequestDispatcher("/dashboard.html").forward(request, response);
    }
}
