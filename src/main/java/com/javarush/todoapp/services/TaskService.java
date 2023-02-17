package com.javarush.todoapp.services;

import com.javarush.todoapp.dto.TaskDto;
import com.javarush.todoapp.mappers.TaskMapper;
import com.javarush.todoapp.model.Task;
import com.javarush.todoapp.model.User;
import com.javarush.todoapp.repositories.TaskRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class TaskService {

    private final Logger LOGGER = LogManager.getLogger(TaskService.class);

    TaskRepository taskRepository;
    TaskMapper taskMapper = TaskMapper.INSTANCE;
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        LOGGER.info("Created TaskService");
    }

    public List<TaskDto> getAllTasks(User user) {

        List<Task> taskList = taskRepository.getAll(user.getId());
        List<TaskDto> taskDtoList = new ArrayList<>();

        for (Task task : taskList) {
            TaskDto taskDto = taskMapper.toTaskDto(task);
            taskDtoList.add(taskDto);
        }
        return taskDtoList;
    }

    public TaskDto getTasksById(Long id) {

        TaskDto dto = taskMapper.toTaskDto(taskRepository.getById(id));
        LOGGER.info("TaskDto was get for User with id: {}", id);
        return dto;
    }
}
