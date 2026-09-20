package com.example.habittracker.entity;
import java.time.LocalDateTime;
public class Habit {
    private int id;
    private String name;
    private String description;
    private int target;
    private LocalDateTime createdAt;

    public Habit(
            int id,
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
    public int getId(){
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
