package com.javarush.todoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javarush.todoapp.dto.TaskDto;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashSet;
import java.util.Set;

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

//    @JsonIgnore
//    @ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(schema = "todo", name = "task_teg",
//    inverseJoinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
//    joinColumns = @JoinColumn(name = "teg_id", referencedColumnName = "id"))
//    private Set<Task> tasks;

}
