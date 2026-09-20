package com.example.habittracker.exception;
import org.springframework.http.HttpStatus;

public class HabitNotFoundException extends RuntimeException {

    public HabitNotFoundException(String message) {
        super(message);
    }
}