
# Task tracker 

The Task Tracker is a straightforward Command Line Interface (CLI) designed to help you track and manage your tasks 
efficiently. This application takes user actions and inputs as arguments, storing the tasks in a JSON file. It leverages
Java's built-in file system module to read from and write to the JSON file, ensuring that no external libraries or 
frameworks are required for the project.

## Example

### Adding a new task

```Bash
task-cli add "Buy groceries"
# Output: Task added successfully (ID: 1)
```

### Updating tasks

```Bash
task-cli update 1 "Buy groceries and cook dinner"
```

### Deleting tasks

```Bash
task-cli delete 1
```

### Marking a task as in progress or done

```Bash
task-cli mark-in-progress 1
task-cli mark-done 1
```

### Listing all tasks

```Bash
task-cli list
```

### Listing tasks by status

```Bash
task-cli list done
task-cli list todo
task-cli list in-progress
```