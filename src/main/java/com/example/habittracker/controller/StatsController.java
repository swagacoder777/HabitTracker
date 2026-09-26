package com.example.habittracker.controller;

import com.example.habittracker.dto.StatsResponse;
import com.example.habittracker.service.StatsService;
import org.springframework.web.bind.annotation.*;
import com.example.habittracker.dto.DailyStatsResponse;
import com.example.habittracker.dto.WeekStatsResponse;
@RestController
@RequestMapping("/api")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/habits/{id}/stats")
    public StatsResponse getStats(@PathVariable Long id) {
        return statsService.getStats(id);
    }
    @GetMapping("/stats/daily")
    public DailyStatsResponse getDailyStats() {
        return statsService.getDailyStats();
    }
    @GetMapping("/stats/week")
    public WeekStatsResponse getWeekStats() {
        return statsService.getWeekStats();
    }
}