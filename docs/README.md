# LicM User Guide

LicM is a desktop chatbot application for managing tasks, specialised for users who prefer typing over mouse interactions. It helps you keep track of things to do (todos), deadlines and events through a simple command-line interface(CLI).
Product images are under "images" folder.

- [Quick Start](#quick-start)
- [Features](#features)
    - [Adding a Todo: `todo`](#adding-a-todo-todo)
    - [Adding a Deadline: `deadline`](#adding-a-deadline-deadline)
    - [Adding an Event: `event`](#adding-an-event-event)
    - [Listing all tasks: `list`](#listing-all-tasks-list)
    - [Marking a task as done: `mark`](#marking-a-task-as-done-mark)
    - [Unmarking a task: `unmark`](#unmarking-a-task-unmark)
    - [Deleting a task: `delete`](#deleting-a-task-delete)
    - [Finding tasks: `find`](#finding-tasks-find)
    - [Exiting the program: `bye`](#exiting-the-program-bye)
- [Command Summary](#command-summary)
---

## Quick Start

1. Ensure you have Java 17 or above installed on your computer.
2. Download the latest `LicM.jar` from the [Releases](https://github.com/lab3ll3/ip/releases) page.
3. Copy the JAR file into an empty folder.
4. Open a command terminal in that folder.
5. Run the command: `java -jar LicM.jar` (welcome.png)

---

## Features

> Notes about the command format:
> - Words in `UPPER_CASE` are parameters you need to supply.
> - Parameters must be in the specified order.
> - Dates must be in yyyy-mm-dd format (e.g `2024-12-02`).

---

### Adding a todo: `todo` (todo.png)
Adds a todo task to the task list.

**Format:** `todo DESCRIPTION`

**Example:** todo read book
```
    ____________________________________________________________
    Got it. I've added this task:
      [T][ ] read book
    Now you have 7 tasks in the list.
    ____________________________________________________________
```

### Adding a deadline: `deadline` (deadline.png)
Adds a deadline task with a due date.

**Format:** `deadline DESCRIPTION /by DATE`

:information_source: **Note:** DATE must be in `yyyy-mm-dd` format (e.g., `2024-12-02`).

**Example:** deadline return book /by 2024-12-02
```

    ____________________________________________________________
    Got it. I've added this task:
      [D][ ] return book (by: Dec 2 2024)
    Now you have 8 tasks in the list.
    ____________________________________________________________
```

### Adding an event: `event` (event.png)
Adds an event with a start and end date.

**Format:** `event DESCRIPTION /from START_DATE /to END_DATE`

:information_source: **Note:** START_DATE and END_DATE must be in `yyyy-mm-dd` format.

**Example:** event project meeting /from 2024-12-05 /to 2024-12-07
```
    ____________________________________________________________
    Got it. I've added this task:
      [E][ ] project meeting (from: Dec 5 2024 to: Dec 7 2024)
    Now you have 9 tasks in the list.
    ____________________________________________________________
```

### Listing all tasks: `list` (list.png)
Shows all tasks in the task list.

**Format:** `list`

**Example:** list
```
    ____________________________________________________________
    Here are the tasks in your list:
    1. [T][ ] read book
    2. [D][ ] return book (by: Dec 2 2024)
    3. [E][ ] project meeting (from: Dec 5 2024 to: Dec 7 2024)
    ____________________________________________________________
```

### Marking a task as done: `mark` (mark.png)
Marks a specific task as completed.

**Format:** `mark TASK_NUMBER`

:bulb: **Tip:** Use the `list` command to see the task numbers.

**Example:** mark 1
```
    ____________________________________________________________
    Nice! I've marked this task as done:
      [T][X] read book
    ____________________________________________________________
```

### Unmarking a task: `unmark` (unmark.png)
Marks a specific task as not completed.

**Format:** `unmark TASK_NUMBER`

**Example:** unmark 1
```
    ____________________________________________________________
    OK, I've marked this task as not done yet:
      [T][ ] read book
    ____________________________________________________________
```

### Deleting a task: `delete` (delete.png)
Removes a task from the task list.

**Format:** `delete TASK_NUMBER`

**Example:** delete 2
```
    ____________________________________________________________
    Noted. I've removed this task:
      [D][ ] return book (by: Dec 2 2024)
    Now you have 2 tasks in the list.
    ____________________________________________________________
```

### Finding tasks: `find` (find.png)
Finds tasks whose descriptions contain the given keyword.

**Format:** `find KEYWORD`

:bulb: **Tip:** The search is case-insensitive. e.g., `find book` will match `Book`.

**Examples:** find book
```
    ____________________________________________________________
    Here are the matching tasks in your list:
    1. [T][ ] read book
    ____________________________________________________________
```

### Exiting the program: `bye` (bye.png)
Exits the LicM application.

**Format:** `bye`

**Example:** bye
```
    ____________________________________________________________
    Bye. Hope to see you again soon!
    ____________________________________________________________
```

## Command Summary

| Action | Format | Example |
|--------|--------|---------|
| **Add todo** | `todo DESCRIPTION` | `todo read book` |
| **Add deadline** | `deadline DESCRIPTION /by DATE` | `deadline return book /by 2024-12-02` |
| **Add event** | `event DESCRIPTION /from START /to END` | `event meeting /from 2024-12-05 /to 2024-12-07` |
| **List** | `list` | `list` |
| **Mark** | `mark TASK_NUMBER` | `mark 1` |
| **Unmark** | `unmark TASK_NUMBER` | `unmark 1` |
| **Delete** | `delete TASK_NUMBER` | `delete 2` |
| **Find** | `find KEYWORD` | `find book` |
| **Exit** | `bye` | `bye` |