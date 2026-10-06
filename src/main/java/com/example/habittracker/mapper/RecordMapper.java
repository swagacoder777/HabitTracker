package com.example.habittracker.mapper;

import com.example.habittracker.dto.RecordResponse;
import com.example.habittracker.entity.HabitRecord;

public class RecordMapper {

    public static RecordResponse toResponse(HabitRecord record) {
        return new RecordResponse(
                record.getId(),
                record.getHabit().getId(),
                record.getDate()
        );
    }
}