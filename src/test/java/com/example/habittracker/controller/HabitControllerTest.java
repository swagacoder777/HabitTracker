package com.example.habittracker.controller;

import com.example.habittracker.entity.Habit;
import com.example.habittracker.service.HabitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.example.habittracker.service.CustomUserDetailsService;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.example.habittracker.service.JwtService;
@WebMvcTest(HabitController.class)
class HabitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HabitService habitService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @MockitoBean
    private JwtService jwtService;
    @Test
    void shouldGetExistingHabit() throws Exception {
        Habit habit = new Habit(
                1L,
                "Read",
                "Read a book",
                1,
                LocalDateTime.of(2026, 10, 5, 10, 0)
        );

        when(habitService.getHabit(1L))
                .thenReturn(habit);

        mockMvc.perform(
                        get("/api/habits/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Read"))
                .andExpect(jsonPath("$.description").value("Read a book"))
                .andExpect(jsonPath("$.target").value(1));
    }

    @Test
    void shouldReturn404WhenHabitNotFound() throws Exception {
        when(habitService.getHabit(999L))
                .thenThrow(new com.example.habittracker.exception.HabitNotFoundException(
                        "Привычка не найдена"
                ));

        mockMvc.perform(
                        get("/api/habits/999")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateHabit() throws Exception {
        Habit createdHabit = new Habit(
                1L,
                "Read",
                "Read a book",
                1,
                LocalDateTime.of(2026, 10, 5, 10, 0)
        );

        when(habitService.createHabit(org.mockito.ArgumentMatchers.any(Habit.class)))
                .thenReturn(createdHabit);

        String json = """
                {
                    "name": "Read",
                    "description": "Read a book",
                    "target": 1
                }
                """;

        mockMvc.perform(
                        post("/api/habits")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Read"))
                .andExpect(jsonPath("$.target").value(1));
    }
}