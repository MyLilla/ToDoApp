package com.javarush.todoapp.repositories;

import com.javarush.todoapp.model.Task;
import com.javarush.todoapp.model.Teg;

import java.util.List;
import java.util.Set;

public interface TegRepository {

    void save(Teg teg);

    void joinTegsInTask(Task task, Set<Teg> tegs);

    Set<Teg> getByTitle(String title);
}
