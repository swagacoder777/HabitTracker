package com.example.habittracker.entity;

import java.time.LocalDate;

public class HabitRecord {

    private int id;
    private int habitId;
    private LocalDate date;

    public HabitRecord(int id, int habitId, LocalDate date) {
        this.id = id;
        this.habitId = habitId;
        this.date = date;
    }
    public int getId() {
        return id;
    }

    public int getHabitId() {
        return habitId;
    }

    public LocalDate getDate() {
        return date;
    }
}
