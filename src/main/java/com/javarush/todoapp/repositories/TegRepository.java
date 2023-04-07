package com.javarush.todoapp.repositories;

import com.javarush.todoapp.model.Teg;

public interface TegRepository {

    void save(Teg teg);

    Teg getByTitle(String title);
}
