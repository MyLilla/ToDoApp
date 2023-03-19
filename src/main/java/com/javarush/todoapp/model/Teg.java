package com.javarush.todoapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "todo", name = "teg")
public class Teg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 10)
    private String title;

    @Column(length = 10)
    private String color;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToMany
    @JoinTable(schema = "todo", name = "task_teg",
            inverseJoinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "teg_id", referencedColumnName = "id"))
    private Set<Task> tasks;
}
