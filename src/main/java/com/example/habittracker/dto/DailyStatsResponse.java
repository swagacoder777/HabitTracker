package com.example.habittracker.dto;

import java.time.LocalDate;
import java.util.List;

public class DailyStatsResponse {

    private LocalDate date;
    private List<DailyHabitResponse> habits;

    public DailyStatsResponse(
            LocalDate date,
            List<DailyHabitResponse> habits) {

        this.date = date;
        this.habits = habits;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<DailyHabitResponse> getHabits() {
        return habits;
    }
}