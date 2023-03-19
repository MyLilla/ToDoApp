package com.javarush.todoapp.model;

import com.javarush.todoapp.enums.Priority;
import com.javarush.todoapp.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "todo", name = "task")
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
    @Enumerated
    private Priority priority;
    @CreationTimestamp
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;
    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "todo", name = "task_teg",
            inverseJoinColumns = @JoinColumn(name = "teg_id", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"))
    private Set<Teg> tegs;
}
