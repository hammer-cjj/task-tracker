package com.cf;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Copyright(C) 2024- com.cf
 * FileName:    Data
 * Author:      cf
 * Date:        2024/11/16 11:12
 * Description:
 */
public class DataEntity {
    // Current max Id
    private Long maxId;
    // All task
    private List<Task> tasks = new ArrayList<>();

    public Long getMaxId() {
        return maxId;
    }

    public void setMaxId(Long maxId) {
        this.maxId = maxId;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    private String toJSONString() {
        if (tasks.isEmpty()) {
            return "";
        }
        return tasks.stream()
                .map(Task::toString)
                .collect(Collectors.joining(",\n        "));
    }

    @Override
    public String toString() {
        return "{\n" +
                "    \"maxId\":" + maxId +
                ",\n    \"tasks\":[\n" +
                "        " + toJSONString() +
                "\n    ]" +
                "\n}";
    }
}
