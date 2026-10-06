package com.example.habittracker.controller;

import com.example.habittracker.dto.RecordResponse;
import com.example.habittracker.entity.HabitRecord;
import com.example.habittracker.service.RecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import com.example.habittracker.mapper.RecordMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
@RestController
@RequestMapping("/api/habits/{id}/records")
@SecurityRequirement(name = "bearerAuth")
public class RecordController {

    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }
    @Operation(
            summary = "Create a record",
            description = "Creates a completion record for the current user's habit"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Record created successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Habit not found"
    )
    @PostMapping
    public ResponseEntity<RecordResponse> createRecord(
            @PathVariable Long id) {

        HabitRecord record = recordService.createRecord(id);

        return ResponseEntity
                .status(201)
                .body(RecordMapper.toResponse(record));
    }
    @Operation(
            summary = "Get habit records",
            description = "Returns all completion records for the current user's habit"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Records retrieved successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Habit not found"
    )
    @GetMapping
    public List<RecordResponse> getRecords(
            @PathVariable Long id) {

        return recordService.getRecords(id)
                .stream()
                .map(RecordMapper::toResponse)
                .toList();
    }
}