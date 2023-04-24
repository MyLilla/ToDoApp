package com.javarush.todoapp.services;

import com.javarush.todoapp.dto.*;
import com.javarush.todoapp.enums.*;
import com.javarush.todoapp.exceptions.TaskException;
import com.javarush.todoapp.mappers.*;
import com.javarush.todoapp.model.*;
import com.javarush.todoapp.repositories.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TaskService {

    private final Logger LOGGER = LogManager.getLogger(TaskService.class);
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TegRepository tegRepository;
    private final TaskMapper taskMapper = TaskMapper.INSTANCE;
    private final TegMapper tegMapper = TegMapper.INSTANCE;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, TegRepository tegRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.tegRepository = tegRepository;
        LOGGER.info("Created TaskService");
    }

    public List<TaskDto> getAllTasksDto(Long userId, String countTasks) {

        Integer size;
        try {
            size = Integer.parseInt(countTasks);
        } catch (NumberFormatException exception) {
            LOGGER.debug("Count: {} is not number", countTasks);
            throw new TaskException("Couldn't get all Tasks", exception);
        }
            List<Task> taskList = taskRepository.getAllWithLimit(userId, size);
            LOGGER.info("Got task list: {} for user: {}", taskList, userId);

            List<TaskDto> taskDtoList = new ArrayList<>();
            for (Task task : taskList) {
                TaskDto taskDto = taskMapper.toTaskDto(task);

                Set<TegDto> tegDtos = new HashSet<>();
                for (Teg teg : task.getTegs()) {
                    TegDto tegDto = tegMapper.toTegDto(teg);
                    tegDtos.add(tegDto);
                }
                taskDto.setTegs(tegDtos);
                LOGGER.info("Convert tegs to tegsDto list: {}", tegDtos);

                taskDtoList.add(taskDto);
            }
            LOGGER.info("Convert task to taskDto list: {}", taskDtoList);
        return taskDtoList;
    }

    public Long getCountAllTasks(User user) {

        return taskRepository.getCount(user.getId());
    }

    public TaskDto getTasksDtoById(Long id) {

        TaskDto taskDto = taskMapper.toTaskDto(taskRepository.getById(id));
        LOGGER.info("TaskDto was get for User with id: {}", id);
        return taskDto;
    }

    public void createTask(String title, String description, String hours,
                           String[] tegs, String status, String priority, Long userId) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        if (hours.isEmpty()) {
            hours = "0";
        }
        task.setHours(Short.parseShort(hours));
        task.setStatus(Status.valueOf(status));
        task.setPriority(Priority.valueOf(priority));

        User user = userRepository.getById(userId);
        task.setUserId(user);

        Set<Teg> tegsForTask = getTegsForTask(tegs, task);
        task.setTegs(tegsForTask);

        LOGGER.info("Created new Task: {} for User: {}", task, userId);
        taskRepository.saveOrUpdate(task);
    }

    public void deleteTask(String id) {
        Long taskId = Long.parseLong(id);
        LOGGER.info("Task id was get for delete: {}", id);
        taskRepository.deleteTask(taskId);
    }

    public void updateTask(String title, String description, String hours, String[] tegs,
                           String status, String priority, Long taskId) {

        Task task = taskRepository.getById(taskId);
        LOGGER.info("get Task: {} for updating", task);

        task.setTitle(title);
        task.setDescription(description);
        task.setHours(Short.parseShort(hours));
        task.setStatus(Status.valueOf(status));
        task.setPriority(Priority.valueOf(priority));

        Set<Teg> newTegsForTask = getTegsForTask(tegs, task);
        task.setTegs(newTegsForTask);
        LOGGER.info("update task with id: {} to: {}", taskId, task);

        taskRepository.saveOrUpdate(task);
        LOGGER.info("Task with id: {} was updated in db", task.getId());
    }

    private Set<Teg> getTegsForTask(String[] tegs, Task task) {

        Set<Teg> newTegsForTask = new HashSet<>();
        if (tegs != null) {
            for (String tegName : tegs) {
                Teg teg = tegRepository.getByTitle(tegName);
                newTegsForTask.add(teg);
            }
        }
        LOGGER.info("Got tegs: {} for task: {}", newTegsForTask, task);
        return newTegsForTask;
    }
}
