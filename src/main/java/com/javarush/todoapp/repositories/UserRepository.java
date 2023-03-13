package com.javarush.todoapp.repositories;

import com.javarush.todoapp.model.User;

public interface UserRepository {

    void saveUser(User user);
    User getWithPassword(String login, String password);
    User getById(Long id);
}
