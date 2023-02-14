package com.javarush.todoapp.dto;

import com.javarush.todoapp.model.Task;

import java.util.Set;

public class UserDto {

    private Long id;
    private String userName;
    private String login;
    private Set<Task> tasks;
}
