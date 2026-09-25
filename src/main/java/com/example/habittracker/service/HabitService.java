package com.example.habittracker.service;

import com.example.habittracker.entity.Habit;
import com.example.habittracker.exception.HabitNotFoundException;
import com.example.habittracker.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitService {

    private final HabitRepository habitRepository;

    public HabitService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    public List<Habit> getHabits() {
        return habitRepository.findAll();
    }

    public Habit getHabit(Long id) {
        return habitRepository.findById(id)
                .orElseThrow(() ->
                        new HabitNotFoundException(
                                "Привычка не найдена"
                        ));
    }

    public Habit createHabit(Habit habit) {
        return habitRepository.save(habit);
    }

    public Habit updateHabit(Long id, Habit habit) {

        Habit existing = getHabit(id);

        return habitRepository.save(
                new Habit(
                        id,
                        habit.getName(),
                        habit.getDescription(),
                        habit.getTarget(),
                        existing.getCreatedAt()
                )
        );
    }

    public void deleteHabit(Long id) {

        Habit habit = getHabit(id);

        habitRepository.delete(habit);
    }
}