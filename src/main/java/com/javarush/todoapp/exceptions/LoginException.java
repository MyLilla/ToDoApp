package com.javarush.todoapp.exceptions;

public class LoginException extends RuntimeException {

    public LoginException(String message) {
        super(message);
    }
}
