package com.example.habittracker.service;

import com.example.habittracker.entity.Habit;
import com.example.habittracker.exception.HabitNotFoundException;
import com.example.habittracker.repository.HabitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.habittracker.entity.User;
import com.example.habittracker.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HabitServiceTest {

    @Autowired
    private HabitService habitService;

    @Autowired
    private HabitRepository habitRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        habitRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        userRepository.save(user);

        var authentication = new UsernamePasswordAuthenticationToken(
                "testuser",
                null,
                java.util.List.of()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldCreateHabit() {

        Habit habit = new Habit(
                null,
                "Пить воду",
                "2 литра в день",
                7,
                LocalDateTime.now()
        );

        Habit saved = habitService.createHabit(habit);

        assertNotNull(saved.getId());
    }

    @Test
    void shouldFindExistingHabit() {

        Habit habit = new Habit(
                null,
                "Читать",
                "20 страниц",
                7,
                LocalDateTime.now()
        );

        Habit saved = habitService.createHabit(habit);

        Habit found = habitService.getHabit(saved.getId());

        assertEquals(saved.getId(), found.getId());
    }

    @Test
    void shouldThrowWhenHabitNotFound() {

        assertThrows(
                HabitNotFoundException.class,
                () -> habitService.getHabit(999L)
        );
    }
}