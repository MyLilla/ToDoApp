package com.javarush.todoapp.services;

import com.javarush.todoapp.model.User;
import com.javarush.todoapp.repositories.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
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
        String hashPassword = DigestUtils.md5Hex(password);
        user.setPassword(hashPassword);

        LOGGER.info("Created new user: {}", user);
        userRepository.saveUser(user);
        return user;
    }

    public User getUserWithPassword(String login, String password) {
        String hashPassword = DigestUtils.md5Hex(password);
        return userRepository.getWithPassword(login, hashPassword);
    }

    public void updateUser(User user) {
        userRepository.saveUser(user);
    }
}
