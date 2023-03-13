package com.javarush.todoapp.services;

import com.javarush.todoapp.dto.TegDto;
import com.javarush.todoapp.dto.UserDto;
import com.javarush.todoapp.mappers.TegMapper;
import com.javarush.todoapp.mappers.UserMapper;
import com.javarush.todoapp.model.Teg;
import com.javarush.todoapp.model.User;
import com.javarush.todoapp.repositories.TegRepository;
import com.javarush.todoapp.repositories.UserRepository;
import com.javarush.todoapp.repositories.hibernateImpl.TegHibernateRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TegService {

    private final Logger LOGGER = LogManager.getLogger(TaskService.class);
    private final TegMapper tegMapper = TegMapper.INSTANCE;
    private UserRepository userRepository;
    private TegRepository tegRepository;

    public TegService(TegRepository tegRepository, UserRepository userRepository) {
        this.tegRepository = tegRepository;
        this.userRepository = userRepository;
        LOGGER.info("Created TegRepository");
    }
    public void createTeg(String teg, String color, Long userId) {

        User user = userRepository.getById(userId);

        Teg newTeg = new Teg();
        newTeg.setTitle(teg);
        newTeg.setColor(color);
        newTeg.setUserId(user);
        LOGGER.info("Created new Teg: {}", newTeg);

        tegRepository.save(newTeg);

        user.getTegs().add(newTeg);
        LOGGER.info("Added new teg: {} to user: {}", newTeg, user);
    }
    public List<TegDto> getAllUsersTegs(Long userId) {

        List<Teg> tegList = tegRepository.getAll(userId);
        List<TegDto> tegDtoList = new ArrayList<>();

        for (Teg teg : tegList) {
            LOGGER.info("Got tags: {}", teg);
            TegDto tegDto = tegMapper.toTegDto(teg);
            LOGGER.info("Got tegDto: {}", tegDto);
            tegDtoList.add(tegDto);
        }
        return tegDtoList;
    }
    public Set<Teg> getSetTegs(String tegs, Long userId) {

        Set<Teg> taskTegSet = new HashSet<>();
        for (String tegStr : tegs.split(", ")) {
            Teg newTeg = new Teg();
            newTeg.setTitle(tegStr);
            tegRepository.save(newTeg);
            LOGGER.info("New teg: {}", newTeg);
        }
        return taskTegSet;
    }
}
