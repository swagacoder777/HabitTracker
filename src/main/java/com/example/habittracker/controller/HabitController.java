package com.example.habittracker.controller;

import com.example.habittracker.dto.HabitRequest;
import com.example.habittracker.dto.HabitResponse;
import com.example.habittracker.entity.Habit;
import com.example.habittracker.mapper.HabitMapper;
import com.example.habittracker.service.HabitService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/habits")
@SecurityRequirement(name = "bearerAuth")
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @GetMapping
    public List<HabitResponse> getHabits() {

        return habitService.getHabits()
                .stream()
                .map(HabitMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public HabitResponse getHabit(
            @PathVariable Long id) {

        return HabitMapper.toResponse(
                habitService.getHabit(id)
        );
    }

    @PostMapping
    public ResponseEntity<HabitResponse> createHabit(
            @Valid @RequestBody HabitRequest request) {

        Habit habit = new Habit(
                null,
                request.getName(),
                request.getDescription(),
                request.getTarget(),
                LocalDateTime.now()
        );

        Habit created = habitService.createHabit(habit);

        return ResponseEntity
                .status(201)
                .body(HabitMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public HabitResponse updateHabit(
            @PathVariable Long id,
            @Valid @RequestBody HabitRequest request) {

        Habit habit = new Habit(
                id,
                request.getName(),
                request.getDescription(),
                request.getTarget(),
                null
        );

        return HabitMapper.toResponse(
                habitService.updateHabit(id, habit)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHabit(
            @PathVariable Long id) {

        habitService.deleteHabit(id);

        return ResponseEntity.noContent().build();
    }
}