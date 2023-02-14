package com.javarush.todoapp.services;

import com.javarush.todoapp.dto.TaskDto;
import com.javarush.todoapp.mappers.TaskMapper;
import com.javarush.todoapp.model.Task;
import com.javarush.todoapp.model.User;
import com.javarush.todoapp.repositories.TaskRepository;
import com.javarush.todoapp.repositories.UserRepository;

import java.util.List;

public class TaskService {

    TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskDto> getAllTasks(User user) {

        taskRepository.getAll(user.getId());
        return null;
    }

    public TaskDto getTasksById(Long id) {

        taskRepository.getById(1L);

        TaskMapper productMapper = TaskMapper.INSTANCE;

        TaskDto dto = productMapper.toProductDTO(taskRepository.getById(1L));

        return dto;
    }
}
