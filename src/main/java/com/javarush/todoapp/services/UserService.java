package com.javarush.todoapp.services;

import com.javarush.todoapp.dto.UserDto;
import com.javarush.todoapp.mappers.UserMapper;
import com.javarush.todoapp.model.User;
import com.javarush.todoapp.repositories.UserRepository;
import com.javarush.todoapp.repositories.hibernateImpl.UserHibernateRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.apache.commons.lang3.ObjectUtils.anyNull;

public class UserService {

    private final Logger LOGGER = LogManager.getLogger(UserService.class);
    private UserRepository userRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;

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

        if (anyNull(login, password)) {
            LOGGER.debug("Login: {} or password: {} is null", login, password);
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
