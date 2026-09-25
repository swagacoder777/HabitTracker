package com.example.habittracker.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "records")
public class HabitRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_id", nullable = false)
    private Habit habit;

    private LocalDate date;

    public HabitRecord() {
    }

    public HabitRecord(Habit habit, LocalDate date) {
        this.habit = habit;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public Habit getHabit() {
        return habit;
    }

    public LocalDate getDate() {
        return date;
    }
}