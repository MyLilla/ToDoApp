package com.javarush.todoapp.exceptions;

public class DbLoudException extends RuntimeException {

    public DbLoudException(String message) {
        super(message);
    }
}
