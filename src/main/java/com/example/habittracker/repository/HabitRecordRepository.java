package com.example.habittracker.repository;

import com.example.habittracker.entity.HabitRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
public interface HabitRecordRepository
        extends JpaRepository<HabitRecord, Long> {
    long countByDate(LocalDate date);

    List<HabitRecord> findByHabitId(Long habitId);

    boolean existsByHabitIdAndDate(Long habitId, LocalDate date);
    @Query("""
    SELECT r
    FROM HabitRecord r
    WHERE r.habit.id = :habitId
      AND r.date >= :startDate
""")
    List<HabitRecord> findRecordsFromDate(
            Long habitId,
            LocalDate startDate
    );
}