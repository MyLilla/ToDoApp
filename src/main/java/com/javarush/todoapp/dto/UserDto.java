package com.javarush.todoapp.dto;

import com.javarush.todoapp.model.Task;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String userName;
    private String login;
    private Set<Task> tasks;
}
