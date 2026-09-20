package com.example.habittracker.repository;

import com.example.habittracker.entity.HabitRecord;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public class HabitRecordRepository {

    public HabitRecord createRecord(int habitId) {
        return new HabitRecord(
                1,
                habitId,
                java.time.LocalDate.now()
        );
    }
    public List<HabitRecord> getRecords(int habitId) {
        return List.of(
                new HabitRecord(
                        1,
                        habitId,
                        java.time.LocalDate.now()
                )
        );
    }

}