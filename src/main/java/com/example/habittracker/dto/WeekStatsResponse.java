package com.example.habittracker.dto;

import java.util.List;

public class WeekStatsResponse {

    private List<WeekDayResponse> week;

    public WeekStatsResponse(List<WeekDayResponse> week) {
        this.week = week;
    }

    public List<WeekDayResponse> getWeek() {
        return week;
    }
}