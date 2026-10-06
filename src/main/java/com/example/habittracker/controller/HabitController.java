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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
@RestController
@RequestMapping("/api/habits")
@SecurityRequirement(name = "bearerAuth")
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @Operation(
            summary = "Get all habits",
            description = "Returns all habits of the current user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Habits retrieved successfully"
    )
    @GetMapping
    public List<HabitResponse> getHabits() {

        return habitService.getHabits()
                .stream()
                .map(HabitMapper::toResponse)
                .toList();
    }
    @Operation(
            summary = "Get habit by ID",
            description = "Returns a habit of the current user by ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Habit found"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Habit not found"
    )
    @GetMapping("/{id}")
    public HabitResponse getHabit(
            @PathVariable Long id) {

        return HabitMapper.toResponse(
                habitService.getHabit(id)
        );
    }
    @Operation(
            summary = "Create a habit",
            description = "Creates a new habit for the current user"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Habit created successfully"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid request data"
    )
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
    @Operation(
            summary = "Update a habit",
            description = "Updates a habit of the current user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Habit updated successfully"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid request data"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Habit not found"
    )
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
    @Operation(
            summary = "Delete a habit",
            description = "Deletes a habit of the current user"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Habit deleted successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Habit not found"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHabit(
            @PathVariable Long id) {

        habitService.deleteHabit(id);

        return ResponseEntity.noContent().build();
    }
}