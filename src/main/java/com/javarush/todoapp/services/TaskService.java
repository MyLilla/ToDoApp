package com.javarush.todoapp.services;

import com.javarush.todoapp.dto.TaskDto;
import com.javarush.todoapp.enums.Priority;
import com.javarush.todoapp.enums.Status;
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

    public List<TaskDto> getAllTasks(User user, String countTasks) {

        Integer size = Integer.parseInt(countTasks);
        List<Task> taskList = taskRepository.getAllWithLimit(user.getId(), size);
        List<TaskDto> taskDtoList = new ArrayList<>();

        for (Task task : taskList) {
            LOGGER.info("Got task: {}", task);
            TaskDto taskDto = taskMapper.toTaskDto(task);
            LOGGER.info("Got taskDto: {}", taskDto);
            taskDtoList.add(taskDto);
        }
        return taskDtoList;
    }

    public Long getCountAllTasks(User user) {

        return taskRepository.getCount(user.getId());
    }

    public TaskDto getTasksById(Long id) {

        TaskDto dto = taskMapper.toTaskDto(taskRepository.getById(id));
        LOGGER.info("TaskDto was get for User with id: {}", id);
        return dto;
    }

    public void createTask(String title, String description, String hours,
                           String tags, String priority, User user) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setHours(Short.parseShort(hours));
        task.setStatus(Status.CREATED);
        task.setPriority(Priority.valueOf(priority));
        task.setUserId(user);

        taskRepository.saveNewTask(task);
    }

    public void deleteTask(String id) {
        Long taskId = Long.parseLong(id);

        taskRepository.deleteTask(taskId);
    }
}
