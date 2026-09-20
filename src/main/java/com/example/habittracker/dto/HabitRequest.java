package com.example.habittracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class HabitRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @Positive
    private int target;

    public HabitRequest(String name, String description, int target) {
        this.name = name;
        this.description = description;
        this.target = target;
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
}