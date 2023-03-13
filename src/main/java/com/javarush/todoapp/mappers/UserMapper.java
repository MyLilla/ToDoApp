package com.javarush.todoapp.mappers;

import com.javarush.todoapp.dto.UserDto;
import com.javarush.todoapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(User user);

    User mapToEntity(UserDto userDto);
}