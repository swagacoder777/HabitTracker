package com.example.habittracker.service;

import com.example.habittracker.entity.HabitRecord;
import com.example.habittracker.repository.HabitRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {

    private final HabitRecordRepository habitRecordRepository;
    private final HabitService habitService;

    public RecordService(
            HabitRecordRepository habitRecordRepository,
            HabitService habitService) {

        this.habitRecordRepository = habitRecordRepository;
        this.habitService = habitService;
    }

    public HabitRecord createRecord(int habitId) {
        habitService.getHabit(habitId);

        return habitRecordRepository.createRecord(habitId);
    }

    public List<HabitRecord> getRecords(int habitId) {
        habitService.getHabit(habitId);

        return habitRecordRepository.getRecords(habitId);
    }
}