package com.cf;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Copyright(C) 2024- com.cf
 * FileName:    HandleCommand
 * Author:      cf
 * Date:        2024/11/15 22:27
 * Description: Handle for command
 */
public class HandleCommand {

    /**
     * Execute command
     */
    public static void execute(String input) {
        // Add task
        if (input.startsWith("task-cli add ")) {
            String arg = input.replace("task-cli add ", "");
            if (arg.startsWith("\"") && arg.endsWith("\"")) {
                arg = arg.substring(1, arg.length() - 1);
                if (arg.isEmpty() || arg.contains("\"")) {
                    throw new TaskException("Error: invalid argument task-cli add");
                }
            }

            DataEntity dataEntity;
            // Load data from a json file
            try {
                dataEntity = TaskJSONFileUtil.readFromJSONFile();
            } catch (IOException e) {
                throw new TaskException("Error: reading from a JSON file: " + e);
            }

            Task task = new Task(
                    arg,
                    TaskStatus.TODO,
                    LocalDateTime.now(),
                    LocalDateTime.now());

            try {
                TaskJSONFileUtil.writeToJSONFile(dataEntity, task);
            } catch (IOException e) {
                throw new TaskException("Error: writing to a JSON file: " + e);
            }
        } else {
            System.err.println("Usage: task-cli <command> <arguments>");
            System.err.println("Please enter q to quit.");
        }
    }
}
