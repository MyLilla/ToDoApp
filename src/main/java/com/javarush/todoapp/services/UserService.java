package com.javarush.todoapp.services;

import com.javarush.todoapp.model.User;
import com.javarush.todoapp.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserService {

    private final Logger LOGGER = LogManager.getLogger(UserService.class);
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        LOGGER.info("Created UserService");
    }

    public User createUser(String name, String login, String password) {
        User user = new User();

        user.setUserName(name);
        user.setLogin(login);
        user.setPassword(password);
        userRepository.saveUser(user);

        return user;
    }

    public User getUserWithPassword(String login, String password) {
        return userRepository.getWithPassword(login, password);
    }
}
