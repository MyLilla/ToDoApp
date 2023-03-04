package com.javarush.todoapp.dto;

import com.javarush.todoapp.enums.Priority;
import com.javarush.todoapp.enums.Status;
import com.javarush.todoapp.model.Comment;
import com.javarush.todoapp.model.Teg;
import com.javarush.todoapp.model.User;
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
    private Set<Teg> tegs;
    private Priority priority;
    private Set<Comment> comments;
    private String createDate;

}
