package com.javarush.todoapp.mappers;

import com.javarush.todoapp.dto.TegDto;
import com.javarush.todoapp.model.Teg;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TegMapper {
    TegMapper INSTANCE = Mappers.getMapper(TegMapper.class);

    TegDto toTegDto(Teg teg);

    Teg mapToEntity(TegDto tegDto);
}
