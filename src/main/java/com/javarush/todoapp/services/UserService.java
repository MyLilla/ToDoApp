package com.javarush.todoapp.services;

import com.javarush.todoapp.dto.UserDto;
import com.javarush.todoapp.mappers.UserMapper;
import com.javarush.todoapp.model.User;
import com.javarush.todoapp.repositories.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import static org.apache.commons.lang3.ObjectUtils.anyNull;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

public class UserService {

    private final Logger LOGGER = LogManager.getLogger(UserService.class);
    private UserRepository userRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        LOGGER.info("Created UserService");
    }

    public User createUser(String name, String login, String password) {

        if(anyNull(name, login, password)) {
            LOGGER.debug("One of attribute is null. name: {}, login: {}, password: {}",
                    name, login, password);
            return null;
        }
        if(name.isEmpty() || login.isEmpty() || password.isEmpty()) {
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
        userRepository.saveUser(user);

        return user;
    }

    public User getUserWithPassword(String login, String password) {

        if(anyNull(login, password)) {
            LOGGER.debug("One of attribute is null. login: {}, password: {}",
                    login, password);
            return null;
        }
        if(login.isEmpty() || password.isEmpty()) {
            LOGGER.debug("One of attribute is empty. login: '{}', password: '{}'",
                   login, password);
            return null;
        }

        String hashPassword = DigestUtils.md5Hex(password);
        User user = userRepository.getWithPassword(login, hashPassword);
        return user;
    }

    public void updateUser(Long userId) {

        User user = userRepository.getById(userId);
        userRepository.saveUser(user);
    }
}
