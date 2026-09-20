package com.example.habittracker.dto;

import java.time.LocalDateTime;

public class HabitResponse {

    private Long id;
    private String name;
    private String description;
    private int target;
    private LocalDateTime createdAt;

    public HabitResponse(
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
}