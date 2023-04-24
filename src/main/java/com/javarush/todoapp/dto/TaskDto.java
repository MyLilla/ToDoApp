package com.javarush.todoapp.dto;

import com.javarush.todoapp.enums.Priority;
import com.javarush.todoapp.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TaskDto {

    private Long id;
    private String title;
    private String description;
    private Short hours;
    private Status status;
    private Priority priority;
    private String createDate;
    private Set<TegDto> tegs;
}
