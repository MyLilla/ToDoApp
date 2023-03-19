package com.javarush.todoapp.repositories;

import com.javarush.todoapp.model.Task;

import java.util.List;

public interface TaskRepository {

    List<Task> getAllWithLimit(Long id, Integer pageSize);

    Long getCount(Long id);

    Task getById(Long id);

    Long saveOrUpdate(Task task);

    void deleteTask(Long id);
}
