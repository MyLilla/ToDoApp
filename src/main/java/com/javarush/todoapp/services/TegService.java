package com.javarush.todoapp.services;

import com.javarush.todoapp.dto.TegDto;
import com.javarush.todoapp.mappers.TegMapper;
import com.javarush.todoapp.model.Teg;
import com.javarush.todoapp.model.User;
import com.javarush.todoapp.repositories.TegRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TegService {

    private final Logger LOGGER = LogManager.getLogger(TaskService.class);
    TegMapper tegMapper = TegMapper.INSTANCE;
    private TegRepository tegRepository;

    public TegService(TegRepository tegRepository) {
        this.tegRepository = tegRepository;
        LOGGER.info("Created TegRepository");
    }
    public void createTeg(String teg, String color, User user) {

        Teg newTeg = new Teg();
        newTeg.setTitle(teg);
        newTeg.setColor(color);
        LOGGER.info("Created new Teg: {}", newTeg);

        tegRepository.save(newTeg);

        user.getTegs().add(newTeg);
        LOGGER.info("Added new teg: {} to user: {}", newTeg, user);
    }
    public List<TegDto> getAllUsersTegs(User user) {

        List<Teg> tegList = tegRepository.getAll(user.getId());
        List<TegDto> tegDtoList = new ArrayList<>();

        for (Teg teg : tegList) {
            LOGGER.info("Got tags: {}", teg);
            TegDto tegDto = tegMapper.toTegDto(teg);
            LOGGER.info("Got tegDto: {}", tegDto);
            tegDtoList.add(tegDto);
        }
        return tegDtoList;
    }
    public Set<Teg> getSetTegs(String tegs, User user) {

        Set<Teg> taskTegSet = new HashSet<>();
        for (String tegStr : tegs.split(", ")) {
            Teg newTeg = new Teg();
            newTeg.setTitle(tegStr);
            tegRepository.save(newTeg);
            System.out.println("New teg: " +newTeg);
        }
        return taskTegSet;
    }
}
