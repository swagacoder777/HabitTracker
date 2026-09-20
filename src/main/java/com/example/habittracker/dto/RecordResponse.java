package com.example.habittracker.dto;

import java.time.LocalDate;

public class RecordResponse {

    private Long id;
    private Long habitId;
    private LocalDate date;

    public RecordResponse(Long id, Long habitId, LocalDate date) {
        this.id = id;
        this.habitId = habitId;
        this.date = date;
    }
    public Long getId() {
        return id;
    }

    public Long getHabitId() {
        return habitId;
    }

    public LocalDate getDate() {
        return date;
    }

}
