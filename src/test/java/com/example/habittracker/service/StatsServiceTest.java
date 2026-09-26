package com.example.habittracker.service;

import org.springframework.boot.test.context.SpringBootTest;
import com.example.habittracker.repository.HabitRecordRepository;
import com.example.habittracker.repository.HabitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.habittracker.entity.Habit;
import com.example.habittracker.entity.HabitRecord;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.example.habittracker.dto.StatsResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
class StatsServiceTest {
    @Autowired
    private StatsService statsService;

    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private HabitRecordRepository habitRecordRepository;
    @BeforeEach
    void setUp() {
        habitRecordRepository.deleteAll();
        habitRepository.deleteAll();
    }
    private Habit createHabit(LocalDateTime createdAt) {

        Habit habit = new Habit(
                null,
                "Пить воду",
                "2 литра в день",
                7,
                createdAt
        );

        return habitRepository.save(habit);
    }
    private void addRecord(Habit habit, LocalDate date) {

        HabitRecord record = new HabitRecord(habit, date);

        habitRecordRepository.save(record);
    }
    @Test
    void shouldCalculateCurrentStreak() {

        Habit habit = createHabit(
                LocalDateTime.now().minusDays(5)
        );

        addRecord(habit, LocalDate.now());
        addRecord(habit, LocalDate.now().minusDays(1));
        addRecord(habit, LocalDate.now().minusDays(2));

        StatsResponse stats =
                statsService.getStats(habit.getId());

        assertEquals(3, stats.getCurrentStreak());
    }
    @Test
    void shouldBreakCurrentStreakAfterMissedDay() {

        Habit habit = createHabit(
                LocalDateTime.now().minusDays(5)
        );

        addRecord(habit, LocalDate.now());
        addRecord(habit, LocalDate.now().minusDays(1));

        addRecord(habit, LocalDate.now().minusDays(3));

        StatsResponse stats =
                statsService.getStats(habit.getId());

        assertEquals(2, stats.getCurrentStreak());
    }
    @Test
    void shouldCalculateBestStreak() {

        Habit habit = createHabit(
                LocalDateTime.now().minusDays(10)
        );

        addRecord(habit, LocalDate.now().minusDays(5));
        addRecord(habit, LocalDate.now().minusDays(4));
        addRecord(habit, LocalDate.now().minusDays(3));

        addRecord(habit, LocalDate.now().minusDays(1));
        addRecord(habit, LocalDate.now());

        StatsResponse stats =
                statsService.getStats(habit.getId());

        assertEquals(3, stats.getBestStreak());
    }
    @Test
    void shouldCalculateCompletionRate() {

        Habit habit = createHabit(
                LocalDateTime.now().minusDays(4)
        );

        addRecord(habit, LocalDate.now());
        addRecord(habit, LocalDate.now().minusDays(1));
        addRecord(habit, LocalDate.now().minusDays(3));

        StatsResponse stats =
                statsService.getStats(habit.getId());

        assertEquals(60.0, stats.getCompletionRate());
    }

}