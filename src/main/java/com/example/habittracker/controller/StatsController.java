package com.example.habittracker.controller;

import com.example.habittracker.dto.StatsResponse;
import com.example.habittracker.service.StatsService;
import org.springframework.web.bind.annotation.*;
import com.example.habittracker.dto.DailyStatsResponse;
import com.example.habittracker.dto.WeekStatsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
@RestController
@RequestMapping("/api")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }
    @Operation(
            summary = "Get habit statistics",
            description = "Returns statistics for a specific habit"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Statistics retrieved successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Habit not found"
    )
    @GetMapping("/habits/{id}/stats")
    public StatsResponse getStats(@PathVariable Long id) {
        return statsService.getStats(id);
    }
    @Operation(
            summary = "Get daily statistics",
            description = "Returns today's statistics for the current user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Daily statistics retrieved successfully"
    )
    @GetMapping("/stats/daily")
    public DailyStatsResponse getDailyStats() {
        return statsService.getDailyStats();
    }
    @Operation(
            summary = "Get weekly statistics",
            description = "Returns statistics for the last 7 days for the current user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Weekly statistics retrieved successfully"
    )
    @GetMapping("/stats/week")
    public WeekStatsResponse getWeekStats() {
        return statsService.getWeekStats();
    }
}