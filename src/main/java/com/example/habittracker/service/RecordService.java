package com.example.habittracker.service;

import com.example.habittracker.entity.Habit;
import com.example.habittracker.entity.HabitRecord;
import com.example.habittracker.exception.HabitNotFoundException;
import com.example.habittracker.repository.HabitRecordRepository;
import com.example.habittracker.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
@Service
public class RecordService {

    private final HabitRecordRepository habitRecordRepository;
    private final HabitRepository habitRepository;

    public RecordService(
            HabitRecordRepository habitRecordRepository,
            HabitRepository habitRepository) {

        this.habitRecordRepository = habitRecordRepository;
        this.habitRepository = habitRepository;
    }
    @Transactional
    public HabitRecord createRecord(Long habitId) {

        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() ->
                        new HabitNotFoundException(
                                "Привычка не найдена"
                        ));

        LocalDate today = LocalDate.now();

        if (habitRecordRepository.existsByHabitIdAndDate(
                habitId, today)) {

            throw new IllegalStateException(
                    "Сегодня привычка уже отмечена"
            );
        }

        HabitRecord record = new HabitRecord(habit, today);

        return habitRecordRepository.save(record);
    }

    public List<HabitRecord> getRecords(Long habitId) {

        if (!habitRepository.existsById(habitId)) {
            throw new HabitNotFoundException(
                    "Привычка не найдена"
            );
        }

        return habitRecordRepository.findByHabitId(habitId);
    }
}