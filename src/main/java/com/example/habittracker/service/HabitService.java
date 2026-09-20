package com.example.habittracker.service;

import com.example.habittracker.entity.Habit;
import com.example.habittracker.exception.HabitNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HabitService {

    private static final Logger log = LoggerFactory.getLogger(HabitService.class);

    private final Map<Long, Habit> habits = new HashMap<>();

    private long nextHabitId = 1;

    public Habit createHabit(Habit habit) {
        long habitId = nextHabitId;

        Habit createdHabit = new Habit(
                (int) habitId,
                habit.getName(),
                habit.getDescription(),
                habit.getTarget(),
                java.time.LocalDateTime.now()
        );

        habits.put(habitId, createdHabit);

        nextHabitId++;

        return createdHabit;
    }

    public Habit getHabit(int id) {

        log.info("Получение привычки с id={}", id);

        Habit habit = habits.get((long) id);

        if (habit == null) {
            throw new HabitNotFoundException("Привычка не найдена");
        }

        return habit;
    }

    public List<Habit> getHabits() {
        return habits.values()
                .stream()
                .toList();
    }

    public Habit updateHabit(int id, Habit habit) {

        Habit existingHabit = habits.get((long) id);

        if (existingHabit == null) {
            throw new HabitNotFoundException("Привычка не найдена");
        }

        Habit updatedHabit = new Habit(
                id,
                habit.getName(),
                habit.getDescription(),
                habit.getTarget(),
                existingHabit.getCreatedAt()
        );

        habits.put((long) id, updatedHabit);

        return updatedHabit;
    }

    public void deleteHabit(int id) {

        Habit removedHabit = habits.remove((long) id);

        if (removedHabit == null) {
            throw new HabitNotFoundException("Привычка не найдена");
        }
    }
}