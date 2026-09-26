package com.example.habittracker.dto;

import java.time.LocalDate;

public class WeekDayResponse {

    private LocalDate date;
    private int completedCount;
    private int totalCount;

    public WeekDayResponse(
            LocalDate date,
            int completedCount,
            int totalCount) {

        this.date = date;
        this.completedCount = completedCount;
        this.totalCount = totalCount;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getCompletedCount() {
        return completedCount;
    }

    public int getTotalCount() {
        return totalCount;
    }
}