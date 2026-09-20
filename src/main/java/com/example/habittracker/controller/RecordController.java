package com.example.habittracker.controller;

import com.example.habittracker.service.RecordService;
import org.springframework.web.bind.annotation.RestController;
import com.example.habittracker.entity.HabitRecord;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.habittracker.dto.RecordResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
@RestController
public class RecordController {

    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping("/api/habits/{id}/records")
    public RecordResponse createRecord(@PathVariable int id) {

        HabitRecord record = recordService.createRecord(id);

        return new RecordResponse(
                (long) record.getId(),
                (long) record.getHabitId(),
                record.getDate()
        );
    }
    @GetMapping("/api/habits/{id}/records")
    public List<RecordResponse> getRecords(@PathVariable int id) {
        return recordService.getRecords(id)
                .stream()
                .map(record -> new RecordResponse(
                        (long) record.getId(),
                        (long) record.getHabitId(),
                        record.getDate()
                ))
                .toList();
    }
}