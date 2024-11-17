package com.cf;

/**
 * Copyright(C) 2024- com.cf
 * FileName:    TaskStatus
 * Author:      cf
 * Date:        2024/11/15 20:35
 * Description: Task Status
 */
public enum TaskStatus {
    DONE("done"), TODO("todo"), IN_PROGRESS("in-progress");
    private final String msg;

    TaskStatus(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public static TaskStatus getTaskStatus(String status) {
        for (TaskStatus taskStatus : TaskStatus.values()) {
            if (taskStatus.getMsg().equals(status)) {
                return taskStatus;
            }
        }
        return null;
    }
}
