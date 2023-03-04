package com.javarush.todoapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(schema = "todo", name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false, length = 15)
    private String userName;
    @Column(unique = true, nullable = false, length = 20)// уникальное поле
    private String login;
    @Column
    private String password;

    @OneToMany(fetch = FetchType.EAGER) // у одного юзера много задач
    @JoinColumn(name = "user_id")
    private Set<Task> tasks;

    @OneToMany (fetch = FetchType.EAGER)// у одного юзера несколько тегов
    @JoinColumn(name = "user_id")
    private Set<Teg> tegs;

}
