package com.javarush.todoapp.services;

import com.javarush.todoapp.exceptions.LongNameException;
import com.javarush.todoapp.model.User;
import com.javarush.todoapp.repositories.UserRepository;
import jakarta.persistence.NoResultException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;

import javax.security.auth.login.LoginException;

import static org.apache.commons.lang3.ObjectUtils.anyNull;

public class UserService {

    private final Logger LOGGER = LogManager.getLogger(UserService.class);
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        LOGGER.info("Created UserService");
    }

    public User createUser(String name, String login, String password) throws LoginException, LongNameException {

        if (anyNull(name, login, password)) {
            LOGGER.debug("One of attribute is null. name: {}, login: {}, password: {}",
                    name, login, password);
            return null;
        }
        if (name.isEmpty() || login.isEmpty() || password.isEmpty()) {
            LOGGER.debug("One of attribute is empty. name: '{}', login: '{}', password: '{}'",
                    name, login, password);
            return null;
        }
        User user = new User();
        user.setUserName(name);
        user.setLogin(login);
        String hashPassword = DigestUtils.md5Hex(password);
        user.setPassword(hashPassword);

        LOGGER.info("Created new user: {}", user);
        try {
            userRepository.saveUser(user);
        } catch (DataException exception) {
            LOGGER.debug("Long user_name: {}", name);
            throw new LongNameException("Long user_name: " + name + exception);
        } catch (ConstraintViolationException exception) {
            LOGGER.debug("User with login: {} already exist", login);
            throw new LoginException("User with login: " + login + " already exist" + exception);
        }
        return user;
    }

    public User getUserWithPassword(String login, String password) throws LoginException {

        try {
            if (anyNull(login, password)) {
                LOGGER.debug("One of attribute is null. login: {}, password: {}",
                        login, password);
                return null;
            }
            if (login.isEmpty() || password.isEmpty()) {
                LOGGER.debug("One of attribute is empty. login: '{}', password: '{}'",
                        login, password);
                return null;
            }

            String hashPassword = DigestUtils.md5Hex(password);
            return userRepository.getWithPassword(login, hashPassword);

        } catch (NoResultException exception) {
            LOGGER.debug("User with login: {} is not exist", login);
            throw new LoginException("User with login: " + login + " is not already exist" + exception);
        }
    }

    public void updateUser(Long userId) throws LoginException {

        User user = userRepository.getById(userId);
        userRepository.saveUser(user);
    }
}
