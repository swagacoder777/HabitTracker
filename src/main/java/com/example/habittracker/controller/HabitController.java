package com.example.habittracker.controller;
import jakarta.validation.Valid;

import com.example.habittracker.dto.HabitRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.habittracker.service.HabitService;
import com.example.habittracker.entity.Habit;
import com.example.habittracker.mapper.HabitMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.example.habittracker.dto.HabitResponse;
@RestController
public class HabitController {

    private final HabitService habitService;
    public HabitController(
            HabitService habitService) {

        this.habitService = habitService;

    }

    @GetMapping("/api/habits")
    public List<HabitResponse> getHabits() {
        return habitService.getHabits()
                .stream()
                .map(HabitMapper::toResponse)
                .toList();
    }

    @GetMapping("/api/habits/{id}")
    public HabitResponse getHabit(@PathVariable int id) {
        Habit habit = habitService.getHabit(id);
        return HabitMapper.toResponse(habit);
    }

    @PostMapping("/api/habits")
    public ResponseEntity<HabitResponse> createHabit(
            @Valid @RequestBody HabitRequest habitRequest) {

        Habit habit = new Habit(
                0,
                habitRequest.getName(),
                habitRequest.getDescription(),
                habitRequest.getTarget(),
                java.time.LocalDateTime.now()
        );

        Habit createdHabit = habitService.createHabit(habit);

        return ResponseEntity
                .status(201)
                .body(HabitMapper.toResponse(createdHabit));
    }

    @PutMapping("/api/habits/{id}")
    public HabitResponse updateHabit(
            @PathVariable int id,
            @Valid @RequestBody HabitRequest habitRequest) {

        Habit habit = new Habit(
                id,
                habitRequest.getName(),
                habitRequest.getDescription(),
                habitRequest.getTarget(),
                java.time.LocalDateTime.now()
        );

        Habit updatedHabit = habitService.updateHabit(id, habit);

        return HabitMapper.toResponse(updatedHabit);
    }

    @DeleteMapping("/api/habits/{id}")
    public ResponseEntity<Void> deleteHabit(@PathVariable int id) {

        habitService.deleteHabit(id);

        return ResponseEntity.noContent().build();
    }

}
