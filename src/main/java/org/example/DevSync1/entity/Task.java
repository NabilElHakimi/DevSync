    package org.example.DevSync1.entity;

    import jakarta.persistence.*;
    import lombok.Data;

    import java.time.LocalDateTime;
    import java.util.List;
    @Entity
    @Table(name = "tasks")
    @Data
    public class Task {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "assigned_to", nullable = false)
        private User assignedTo;

        @Column(nullable = false)
        private String title;
    
        private String description;

        private LocalDateTime dueDate;

        @ManyToMany(mappedBy = "tasks")
        private List<Tag> tags;

        

    }