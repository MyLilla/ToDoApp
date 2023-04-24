package com.javarush.todoapp.mappers;

import com.javarush.todoapp.dto.TaskDto;
import com.javarush.todoapp.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    // @Mapping(target = "tegs", source = "tegs")  // target (taskDto) source - Task
    TaskDto toTaskDto(Task task);

    Task mapToEntity(TaskDto taskDto);
}
