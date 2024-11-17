package com.cf;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright(C) 2024- com.cf
 * FileName:    JSONFileUtil
 * Author:      cf
 * Date:        2024/11/15 22:40
 * Description: Write to and read from a json file
 */
public class TaskJSONFileUtil {

    private static final String FILENAME = "task-data.json";
    private static final Path path = Path.of(FILENAME);

    /**
     * Write to a json file
     * @param dataEntity
     * @throws IOException
     */
    public static void writeToJSONFile(DataEntity dataEntity) throws IOException {;
        Files.writeString(path , dataEntity.toString(), StandardCharsets.UTF_8);
    }

    /**
     * Read from a json file to building a DataEntity Object
     * @return DataEntity or null
     * @throws IOException
     */
    public static DataEntity readFromJSONFile() throws IOException {
        if (Files.exists(path)) {
            DataEntity dataEntity = new DataEntity();
            List<String> strings = Files.readAllLines(path, StandardCharsets.UTF_8);
            // List convert to String
            String s = String.join("", strings);
            // Get maxId
            Pattern pMaxId = Pattern.compile("\"maxId\":(\\d+)");
            Matcher m = pMaxId.matcher(s);
            if (m.find()) {
                dataEntity.setMaxId(Long.parseLong(m.group(1)));
            }
            // Get tasks
            Pattern pTasks = Pattern.compile("\"tasks\":\\[\\s*(.*)\\s*]");
            m = pTasks.matcher(s);
            if (m.find()) {
                List<Task> tasks = dataEntity.getTasks();
                String tasksStr = m.group(1).trim();
                Pattern pTask = Pattern.compile("\\{([^}]*)}");
                m = pTask.matcher(tasksStr);
                while (m.find()) {
                    String t = m.group(1);
                    t = t.replace("\"", "");
                    String[] split = t.split(",");
                    Task task = new Task();
                    // Construct a task object
                    for (String str : split) {
                        String attr = str.split(":", 2)[0];
                        String val = str.split(":", 2)[1];
                        if ("id".equals(attr)) {
                            task.setId(Long.parseLong(val));
                        } else if ("description".equals(attr)) {
                            task.setDescription(val);
                        } else if ("status".equals(attr)) {
                            task.setStatus(TaskStatus.getTaskStatus(val));
                        } else if ("createdAt".equals(attr)) {
                            task.setCreatedAt(LocalDateTime.parse(val,
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        } else if ("updatedAt".equals(attr)) {
                            task.setUpdatedAt(LocalDateTime.parse(val,
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        }
                     }
                    tasks.add(task);
                }
            }
            return dataEntity;
        }

        return null;
    }
}
