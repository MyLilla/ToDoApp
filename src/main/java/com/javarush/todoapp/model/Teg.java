package com.javarush.todoapp.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "todo", name = "tegs")
public class Teg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 10)
    private String title;

    @Column(length = 10)
    private String color;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task taskId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
}
