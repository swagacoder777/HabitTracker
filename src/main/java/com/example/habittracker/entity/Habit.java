package com.example.habittracker.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "habits")
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private int target;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(
            mappedBy = "habit",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<HabitRecord> records = new ArrayList<>();

    public Habit() {
    }

    public Habit(
            Long id,
            String name,
            String description,
            int target,
            LocalDateTime createdAt) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.target = target;
        this.createdAt = createdAt;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getTarget() {
        return target;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<HabitRecord> getRecords() {
        return records;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTarget(int target) {
        this.target = target;
    }
}
