package com.javarush.todoapp.repositories;

import com.javarush.todoapp.model.Teg;

import java.util.List;

public interface TegRepository {

    void save(Teg teg);
    List<Teg> getAll(Long id);
}
