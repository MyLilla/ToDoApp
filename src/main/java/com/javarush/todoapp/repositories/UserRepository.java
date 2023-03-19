package com.javarush.todoapp.repositories;

import com.javarush.todoapp.model.User;

import javax.security.auth.login.LoginException;

public interface UserRepository {

    void saveUser(User user) throws LoginException;

    User getWithPassword(String login, String password) throws LoginException;

    User getById(Long id);
}
