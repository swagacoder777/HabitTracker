package com.example.habittracker.service;

import com.example.habittracker.dto.StatsResponse;
import com.example.habittracker.entity.HabitRecord;
import com.example.habittracker.repository.HabitRecordRepository;
import com.example.habittracker.repository.HabitRepository;
import org.springframework.stereotype.Service;
import com.example.habittracker.entity.Habit;
import com.example.habittracker.exception.HabitNotFoundException;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.List;
import com.example.habittracker.dto.DailyHabitResponse;
import com.example.habittracker.dto.DailyStatsResponse;
import com.example.habittracker.dto.WeekDayResponse;
import com.example.habittracker.dto.WeekStatsResponse;
import java.util.ArrayList;
@Service
public class StatsService {

    private final HabitRecordRepository habitRecordRepository;
    private final HabitRepository habitRepository;

    public StatsService(
            HabitRecordRepository habitRecordRepository,
            HabitRepository habitRepository) {

        this.habitRecordRepository = habitRecordRepository;
        this.habitRepository = habitRepository;
    }
    public WeekStatsResponse getWeekStats() {

        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(6);

        int totalCount = (int) habitRepository.count();

        List<WeekDayResponse> week = new ArrayList<>();

        for (int i = 0; i < 7; i++) {

            LocalDate date = startDate.plusDays(i);

            int completedCount =
                    (int) habitRecordRepository.countByDate(date);

            week.add(new WeekDayResponse(
                    date,
                    completedCount,
                    totalCount
            ));
        }

        return new WeekStatsResponse(week);
    }
    public DailyStatsResponse getDailyStats() {

        LocalDate today = LocalDate.now();

        List<DailyHabitResponse> dailyHabits =
                habitRepository.findAll()
                        .stream()
                        .map(habit -> new DailyHabitResponse(
                                habit.getId(),
                                habit.getName(),
                                habitRecordRepository.existsByHabitIdAndDate(
                                        habit.getId(),
                                        today
                                )
                        ))
                        .toList();

        return new DailyStatsResponse(today, dailyHabits);
    }

    public StatsResponse getStats(Long habitId) {

        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() ->
                        new HabitNotFoundException("Привычка не найдена"));

        List<HabitRecord> records =
                habitRecordRepository.findByHabitId(habitId);

        records.sort(
                (a, b) -> b.getDate().compareTo(a.getDate())
        );

        LocalDate today = LocalDate.now();

        int currentStreak = 0;
        LocalDate expectedDate = today;

        for (HabitRecord record : records) {

            if (record.getDate().equals(expectedDate)) {
                currentStreak++;
                expectedDate = expectedDate.minusDays(1);
            } else {
                break;
            }
        }

        List<HabitRecord> chronologicalRecords =
                habitRecordRepository.findByHabitId(habitId);

        chronologicalRecords.sort(
                (a, b) -> a.getDate().compareTo(b.getDate())
        );

        int bestStreak = 0;
        int currentBest = 0;
        LocalDate previousDate = null;

        for (HabitRecord record : chronologicalRecords) {

            if (previousDate == null) {
                currentBest = 1;
            } else if (record.getDate().equals(previousDate.plusDays(1))) {
                currentBest++;
            } else {
                currentBest = 1;
            }

            bestStreak = Math.max(bestStreak, currentBest);

            previousDate = record.getDate();
        }

        int totalCompletions = records.size();

        LocalDate createdDate =
                habit.getCreatedAt().toLocalDate();

        long totalDays =
                ChronoUnit.DAYS.between(
                        createdDate,
                        LocalDate.now()
                ) + 1;

        double completionRate =
                (double) totalCompletions / totalDays * 100;

        completionRate =
                Math.round(completionRate * 10.0) / 10.0;

        return new StatsResponse(
                currentStreak,
                bestStreak,
                completionRate,
                totalCompletions
        );
    }
}