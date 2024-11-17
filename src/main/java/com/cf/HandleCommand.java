package com.cf;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        input = input.trim();
        try {
            if (input.startsWith("task-cli add")) { // Add task
                String arg = input.replace("task-cli add", "").trim();
                addCommand(arg);
            } else if ("task-cli list".equals(input)) { // Listing all tasks
                listCommand(null);
            } else if ("task-cli list done".equals(input)) { // Listing done tasks
                listCommand("done");
            } else if ("task-cli list todo".equals(input)) { // Listing todo tasks
                listCommand("todo");
            } else if ("task-cli list in-progress".equals(input)) { // Listing in-progress tasks
                listCommand("in-progress");
            } else if (input.startsWith("task-cli update")) {
                String args = input.replace("task-cli update", "");
                updateCommand(args);
            } else {
                System.err.println("Usage: task-cli <command> <arguments>");
                System.err.println("Please enter q to quit.");
            }
        } catch (TaskException | IOException e) {
            System.err.println(e.getMessage());
        }

    }

    // About add command
    private static void addCommand(String arg) throws IOException {
        // Arg must starts with '"' and ends with '"'
        if (arg.startsWith("\"") && arg.endsWith("\"")) {
            // Remove char of index 0 and arg.length() - 1
            arg = arg.substring(1, arg.length() - 1);
            // Arg cann't be empty and contains '"'
            if (arg.isEmpty() || arg.contains("\"")) {
                throw new TaskException("Error: invalid argument task-cli add");
            }

            // Load data from a json file
            DataEntity dataEntity = TaskJSONFileUtil.readFromJSONFile();

            Task task = new Task(
                    arg,
                    TaskStatus.TODO,
                    LocalDateTime.now(),
                    LocalDateTime.now());

            if (dataEntity == null) { // JSON file is not exist, add task at first time
                task.setId(1L);
                dataEntity = new DataEntity();
                dataEntity.setMaxId(1L);
                dataEntity.getTasks().add(task);
            } else { // JSON file is exist
                // Get max id at current time
                Long maxId = dataEntity.getMaxId();
                task.setId(maxId + 1);
                List<Task> tasks = dataEntity.getTasks();
                tasks.add(task);
                dataEntity.setMaxId(maxId + 1);
            }

            TaskJSONFileUtil.writeToJSONFile(dataEntity);
            System.out.println("Task added successfully (ID: " + task.getId() + ")");
        } else {
            throw new TaskException("Error: invalid argument task-cli add");
        }
    }

    // About list command
    private static void listCommand(String arg) throws IOException {
        // Load data from a json file
        DataEntity dataEntity = TaskJSONFileUtil.readFromJSONFile();

        if (dataEntity != null) {
            List<Task> tasks = dataEntity.getTasks();
            if (arg == null) { // listing all tasks
                tasks.forEach(System.out::println);
            } else  { // listing tasks by status
                List<Task> collect = tasks.stream()
                        .filter(item -> item.getStatus().getMsg().equals(arg))
                        .toList();
                collect.forEach(System.out::println);
            }
        }
    }

    // About update command
    private static void updateCommand(String args) throws IOException {
        String[] argsArr = args.trim().split(" ", 2);
        if (argsArr.length != 2) {
            throw new TaskException("Error: invalid argument task-cli update");
        }
        String id = argsArr[0];
        try {
            long taskId = Long.parseLong(id);

            DataEntity dataEntity = TaskJSONFileUtil.readFromJSONFile();

            if (dataEntity == null || dataEntity.getTasks().isEmpty()) {
                throw new TaskException("Task is not exist");
            }

            List<Task> tasks = dataEntity.getTasks();
            // Get the task, otherwise throw a TaskException
            Task task = tasks
                    .stream()
                    .filter(item -> item.getId().equals(taskId))
                    .findFirst()
                    .orElseThrow(
                            () -> new TaskException("Task id " + taskId + " not found"));

            String arg = argsArr[1].trim();
            // Arg must starts with '"' and ends with '"'
            if (arg.startsWith("\"") && arg.endsWith("\"")) {
                // Remove chars of index 0 and arg.length() - 1
                String desc = arg.substring(1, arg.length() - 1);
                // Arg can not be empty and contains '"'
                if (desc.isEmpty() || desc.contains("\"")) {
                    throw new TaskException("Error: invalid argument task-cli update");
                }

                // Description can not be same with other task
                long count = tasks.stream().filter(
                                item -> item.getDescription().equals(desc)
                                        && !item.getId().equals(taskId)).count();

                if (count > 0) {
                    throw new TaskException("Description: " + desc + " is exist");
                }

                // Desc is same with the description of task, return directly
                if (desc.equals(task.getDescription())) {
                    return;
                }

                // Update the task
                task.setDescription(desc);
                task.setUpdatedAt(LocalDateTime.now());

                TaskJSONFileUtil.writeToJSONFile(dataEntity);
            } else {
                throw new TaskException("Error: invalid argument task-cli update");
            }
        } catch (NumberFormatException e) {
            throw new TaskException("Error: task id must be a number");
        }
    }
}
