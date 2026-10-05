package com.example.habittracker.service;

import com.example.habittracker.entity.Habit;
import com.example.habittracker.entity.HabitRecord;
import com.example.habittracker.entity.User;
import com.example.habittracker.exception.HabitNotFoundException;
import com.example.habittracker.repository.HabitRecordRepository;
import com.example.habittracker.repository.HabitRepository;
import com.example.habittracker.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecordService {

    private final HabitRecordRepository habitRecordRepository;
    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    public RecordService(
            HabitRecordRepository habitRecordRepository,
            HabitRepository habitRepository,
            UserRepository userRepository) {

        this.habitRecordRepository = habitRecordRepository;
        this.habitRepository = habitRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public HabitRecord createRecord(Long habitId) {

        User currentUser = getCurrentUser();

        Habit habit = habitRepository.findByIdAndUser_Id(
                habitId,
                currentUser.getId()
        ).orElseThrow(() ->
                new HabitNotFoundException("Привычка не найдена")
        );

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

    private User getCurrentUser() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new HabitNotFoundException("Пользователь не найден")
                );
    }

    public List<HabitRecord> getRecords(Long habitId) {

        User currentUser = getCurrentUser();

        if (!habitRepository.findByIdAndUser_Id(
                habitId,
                currentUser.getId()
        ).isPresent()) {

            throw new HabitNotFoundException(
                    "Привычка не найдена"
            );
        }

        return habitRecordRepository.findByHabitId(habitId);
    }
}