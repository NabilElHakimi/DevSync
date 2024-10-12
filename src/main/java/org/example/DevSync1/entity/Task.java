package org.example.DevSync1.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.DevSync1.enums.Status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "assigned_to", nullable = false)
    private User assignedTo;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private String title;

    private String description;

    private LocalDate dueDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tags_tasks",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    private boolean changed = false;
    private boolean accepted = true ;
    
}
