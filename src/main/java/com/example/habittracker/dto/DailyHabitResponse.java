package com.example.habittracker.dto;

public class DailyHabitResponse {

    private Long id;
    private String name;
    private boolean completed;

    public DailyHabitResponse(
            Long id,
            String name,
            boolean completed) {

        this.id = id;
        this.name = name;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return completed;
    }
}