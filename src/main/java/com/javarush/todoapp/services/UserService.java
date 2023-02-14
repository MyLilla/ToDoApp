package com.javarush.todoapp.services;

import com.javarush.todoapp.model.User;
import com.javarush.todoapp.repositories.UserRepository;

public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
