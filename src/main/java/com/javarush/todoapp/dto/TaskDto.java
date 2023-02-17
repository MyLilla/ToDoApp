package com.javarush.todoapp.dto;

import com.javarush.todoapp.enums.Priority;
import com.javarush.todoapp.enums.Status;
import com.javarush.todoapp.model.Comment;
import com.javarush.todoapp.model.Teg;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class TaskDto {

    private String title;
    private String description;
    private Short hours;
    private Status status;
    private Set<Teg> tegs;
    private Priority priority;
    private Set<Comment> comments;
    private String createDate;

}
