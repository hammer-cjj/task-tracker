package com.cf;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        if (input.startsWith("task-cli add ")) { // Add task
            String arg = input.replace("task-cli add ", "");
            addCommand(arg);
        } else if ("task-cli list".equals(input)) { // Listing all tasks
            listCommand(null);
        } else if ("task-cli list done".equals(input)) {
            listCommand("done");
        } else if ("task-cli list todo".equals(input)) {
            listCommand("todo");
        } else if ("task-cli list in-progress".equals(input)) {
            listCommand("in-progress");
        } else {
            System.err.println("Usage: task-cli <command> <arguments>");
            System.err.println("Please enter q to quit.");
        }
    }

    private static void addCommand(String arg) {
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
    }

    private static void listCommand(String arg) {
        DataEntity dataEntity;
        // Load data from a json file
        try {
            dataEntity = TaskJSONFileUtil.readFromJSONFile();
        } catch (IOException e) {
            throw new TaskException("Error: reading from a JSON file: " + e);
        }
        if (dataEntity != null) {
            List<Task> tasks = dataEntity.getTasks();
            if (arg == null) {
                tasks.forEach(System.out::println);
            } else  {
                List<Task> collect = tasks.stream()
                        .filter(item -> item.getStatus().getMsg().equals(arg))
                        .toList();
                collect.forEach(System.out::println);
            }
        }
    }
}
