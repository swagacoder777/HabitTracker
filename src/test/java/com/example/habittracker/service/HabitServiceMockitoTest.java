package com.example.habittracker.service;

import com.example.habittracker.entity.Habit;
import com.example.habittracker.entity.User;
import com.example.habittracker.exception.HabitNotFoundException;
import com.example.habittracker.repository.HabitRepository;
import com.example.habittracker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HabitServiceMockitoTest {

    @Mock
    private HabitRepository habitRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private HabitService habitService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");

        var authentication = new UsernamePasswordAuthenticationToken(
                "testuser",
                null,
                List.of()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(userRepository.findByUsername("testuser"))
                .thenReturn(Optional.of(user));
    }

    @Test
    void shouldGetHabits() {
        Habit habit = new Habit(
                1L,
                "Пить воду",
                "2 литра",
                7,
                LocalDateTime.now()
        );

        when(habitRepository.findAllByUser_Id(null))
                .thenReturn(List.of(habit));

        List<Habit> result = habitService.getHabits();

        assertEquals(1, result.size());
        assertEquals("Пить воду", result.get(0).getName());
    }

    @Test
    void shouldGetHabit() {
        Habit habit = new Habit(
                1L,
                "Читать",
                "20 страниц",
                7,
                LocalDateTime.now()
        );

        when(habitRepository.findByIdAndUser_Id(1L, null))
                .thenReturn(Optional.of(habit));

        Habit result = habitService.getHabit(1L);

        assertEquals(1L, result.getId());
        assertEquals("Читать", result.getName());
    }
    @Test
    void shouldThrowWhenHabitNotFound() {
        when(habitRepository.findByIdAndUser_Id(999L, null))
                .thenReturn(Optional.empty());

        assertThrows(
                HabitNotFoundException.class,
                () -> habitService.getHabit(999L)
        );
    }
    @Test
    void shouldCreateHabit() {
        Habit habit = new Habit(
                null,
                "Пить воду",
                "2 литра",
                7,
                LocalDateTime.now()
        );

        when(habitRepository.save(habit))
                .thenReturn(habit);

        Habit result = habitService.createHabit(habit);

        assertEquals("Пить воду", result.getName());
        verify(habitRepository).save(habit);
    }
    @Test
    void shouldUpdateHabit() {
        Habit existing = new Habit(
                1L,
                "Старое название",
                "Старое описание",
                5,
                LocalDateTime.now()
        );

        Habit updated = new Habit(
                1L,
                "Новое название",
                "Новое описание",
                7,
                LocalDateTime.now()
        );

        when(habitRepository.findByIdAndUser_Id(1L, null))
                .thenReturn(Optional.of(existing));

        when(habitRepository.save(existing))
                .thenReturn(existing);

        Habit result = habitService.updateHabit(1L, updated);

        assertEquals("Новое название", result.getName());
        assertEquals("Новое описание", result.getDescription());
        assertEquals(7, result.getTarget());

        verify(habitRepository).save(existing);
    }
    @Test
    void shouldDeleteHabit() {
        Habit habit = new Habit(
                1L,
                "Пить воду",
                "2 литра",
                7,
                LocalDateTime.now()
        );

        when(habitRepository.findByIdAndUser_Id(1L, null))
                .thenReturn(Optional.of(habit));

        habitService.deleteHabit(1L);

        verify(habitRepository).delete(habit);
    }
}