package com.javarush.todoapp.services;

import com.javarush.todoapp.model.User;
import com.javarush.todoapp.repositories.UserRepository;

public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser (String name) {
        User user = new User();

        user.setUserName(name);
        user.setLogin("Lilla");
        user.setPassword("321");

        userRepository.saveUser(user);

        return user;
    }
}
