package com.javarush.todoapp.model;

import com.javarush.todoapp.enums.Priority;
import com.javarush.todoapp.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(schema = "todo", name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column
    private Short hours;

    @Enumerated
    private Status status;

    @OneToMany (fetch = FetchType.EAGER)// у одной задачи несколько тегов
    @JoinColumn(name = "teg_id")
    private Set<Teg> tegs;

    @Enumerated
    private Priority priority;

    @OneToMany(fetch = FetchType.EAGER) // у одной задачи может быть много коммитов
    @JoinColumn(name = "comment_id")
    private Set<Comment> comments;
    @CreationTimestamp
    private LocalDateTime createDate;
    @UpdateTimestamp
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

}
