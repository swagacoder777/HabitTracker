package com.example.habittracker.service;

import com.example.habittracker.entity.Habit;
import com.example.habittracker.exception.HabitNotFoundException;
import com.example.habittracker.repository.HabitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.example.habittracker.entity.User;
import com.example.habittracker.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
@Service
public class HabitService {

    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    public HabitService(
            HabitRepository habitRepository,
            UserRepository userRepository
    ) {
        this.habitRepository = habitRepository;
        this.userRepository = userRepository;
    }

    public List<Habit> getHabits() {
        Long userId = getCurrentUser().getId();
        return habitRepository.findAllByUser_Id(userId);
    }

    public Habit getHabit(Long id) {
        Long userId = getCurrentUser().getId();

        return habitRepository.findByIdAndUser_Id(id, userId)
                .orElseThrow(() ->
                        new HabitNotFoundException("Привычка не найдена"));
    }
    @Transactional
    public Habit createHabit(Habit habit) {
        User currentUser = getCurrentUser();
        habit.setUser(currentUser);

        return habitRepository.save(habit);
    }
    @Transactional
    public Habit updateHabit(Long id, Habit habit) {
        Habit existing = getHabit(id);

        existing.setName(habit.getName());
        existing.setDescription(habit.getDescription());
        existing.setTarget(habit.getTarget());

        return habitRepository.save(existing);
    }
    @Transactional
    public void deleteHabit(Long id) {
        Habit habit = getHabit(id);
        habitRepository.delete(habit);
    }
    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new HabitNotFoundException("Пользователь не найден"));
    }
}