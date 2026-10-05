package com.example.habittracker.repository;

import com.example.habittracker.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface HabitRepository extends JpaRepository<Habit, Long> {

    List<Habit> findAllByUser_Id(Long userId);
    Optional<Habit> findByIdAndUser_Id(Long id, Long userId);

}