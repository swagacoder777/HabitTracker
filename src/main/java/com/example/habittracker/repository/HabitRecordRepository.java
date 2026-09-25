package com.example.habittracker.repository;

import com.example.habittracker.entity.HabitRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HabitRecordRepository
        extends JpaRepository<HabitRecord, Long> {

    List<HabitRecord> findByHabitId(Long habitId);

    boolean existsByHabitIdAndDate(Long habitId, LocalDate date);
}