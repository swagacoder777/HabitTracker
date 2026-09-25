package com.example.habittracker.mapper;

import com.example.habittracker.dto.HabitResponse;
import com.example.habittracker.entity.Habit;

public class HabitMapper {

    public static HabitResponse toResponse(Habit habit) {

        return new HabitResponse(
                habit.getId(),
                habit.getName(),
                habit.getDescription(),
                habit.getTarget(),
                habit.getCreatedAt()
        );
    }
}