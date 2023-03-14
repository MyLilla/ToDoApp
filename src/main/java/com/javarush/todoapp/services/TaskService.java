package com.javarush.todoapp.services;

import com.javarush.todoapp.dto.TaskDto;
import com.javarush.todoapp.enums.Priority;
import com.javarush.todoapp.enums.Status;
import com.javarush.todoapp.mappers.TaskMapper;
import com.javarush.todoapp.model.Task;
import com.javarush.todoapp.model.Teg;
import com.javarush.todoapp.model.User;
import com.javarush.todoapp.repositories.TaskRepository;
import com.javarush.todoapp.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TaskService {

    private final Logger LOGGER = LogManager.getLogger(TaskService.class);

    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private final TaskMapper taskMapper = TaskMapper.INSTANCE;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        LOGGER.info("Created TaskService");
    }

    public List<TaskDto> getAllTasks(Long userId, String countTasks) {

        Integer size = Integer.parseInt(countTasks);
        List<Task> taskList = taskRepository.getAllWithLimit(userId, size);
        LOGGER.info("Got task list: {}", taskList);
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

    public void createTask(String title, String description, String hours, Set<Teg> tegs,
                           String status, String priority, Long userId) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setHours(Short.parseShort(hours));
        task.setStatus(Status.valueOf(status));
        task.setPriority(Priority.valueOf(priority));

        User user = userRepository.getById(userId);
        task.setUserId(user);

        Long taskId = taskRepository.saveOrUpdate(task);
        taskRepository.joinTegsInTask(taskId, tegs);
        LOGGER.info("joinTegs: {} InTask: {}", tegs, taskId);
    }

    public void deleteTask(String id) {
        Long taskId = Long.parseLong(id);

        taskRepository.deleteTask(taskId);
    }

    public void updateTask(String title, String description, String hours,
                           String status, String priority, Long taskId) {

        Task task = taskRepository.getById(taskId);
        LOGGER.info("get Task: {} for updating", task);

        task.setTitle(title);
        task.setDescription(description);
        task.setHours(Short.parseShort(hours));
        task.setStatus(Status.valueOf(status));
        task.setPriority(Priority.valueOf(priority));
        LOGGER.info("update task with id: {} to: {}", taskId, task);

        taskRepository.saveOrUpdate(task);
        LOGGER.info("Task with id: {} was updated in db", task.getId());
    }
}
