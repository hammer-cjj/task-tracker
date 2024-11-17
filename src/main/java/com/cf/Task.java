package com.cf;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Copyright(C) 2024- com.cf
 * FileName:    Task
 * Author:      cf
 * Date:        2024/11/15 20:33
 * Description: Task Model
 */
public class Task {
    private Long id;
    private String description;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Task() {
    }

    public Task(String description, TaskStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ",\"description\":\"" + description +
                "\",\"status\":\"" + status.getMsg() +
                "\",\"createdAt\":\"" + createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                "\",\"updatedAt\":\"" + updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                "\"}";
    }
}
