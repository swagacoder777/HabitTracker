package com.example.habittracker.controller;

import com.example.habittracker.dto.RecordResponse;
import com.example.habittracker.entity.HabitRecord;
import com.example.habittracker.service.RecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habits/{id}/records")
public class RecordController {

    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping
    public ResponseEntity<RecordResponse> createRecord(
            @PathVariable Long id) {

        HabitRecord record = recordService.createRecord(id);

        return ResponseEntity
                .status(201)
                .body(new RecordResponse(
                        record.getId(),
                        record.getHabit().getId(),
                        record.getDate()
                ));
    }

    @GetMapping
    public List<RecordResponse> getRecords(
            @PathVariable Long id) {

        return recordService.getRecords(id)
                .stream()
                .map(record -> new RecordResponse(
                        record.getId(),
                        record.getHabit().getId(),
                        record.getDate()
                ))
                .toList();
    }
}