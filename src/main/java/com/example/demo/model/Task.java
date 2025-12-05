package com.example.demo.service;

import jakarta.annotation.Priority;

import java.time.LocalDateTime;

public class Task {
    private int id;
    private String title;
    private Priority priority;
    private boolean completed;
    private LocalDateTime createdAt;

    public Task() {}

    public Task(int id, String title, Priority priority) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.completed = false;
        this.createdAt = LocalDateTime.now();
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Priority getPriority() {
        return priority;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

public enum Priority {
    HIGH,   // Высокий приоритет
    MEDIUM, // Средний приоритет
    LOW     // Низкий приоритет
}

