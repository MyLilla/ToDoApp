package com.javarush.todoapp.repositories;

import com.javarush.todoapp.model.Task;
import com.javarush.todoapp.model.Teg;

import java.util.List;
import java.util.Set;

public interface TaskRepository {

    List<Task> getAllWithLimit(Long id, Integer pageSize);
    Long getCount(Long id);
    Task getById(Long id);
    Long saveOrUpdate(Task task);
    void deleteTask(Long id);
    void joinTegsInTask(Long task_id, Set<Teg> tegs);
}
