package com.javarush.todoapp.model;

import com.javarush.todoapp.enums.Priority;
import com.javarush.todoapp.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "todo", name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column
    private Short hours = 0;

    @Enumerated
    private Status status;

    @OneToMany (fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    private Set<Teg> tegs = new HashSet<>();

    @Enumerated
    private Priority priority;

    @CreationTimestamp
    private LocalDateTime createDate;
    @UpdateTimestamp
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

}
