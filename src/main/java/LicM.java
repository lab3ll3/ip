import exceptions.LicMException;
import task.Deadline;
import task.Event;
import task.Task;
import task.Todo;

import java.util.Scanner;

public class LicM {
    private static final int MAX_TASKS = 100;
    private static final Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;
    private static final Scanner scanner = new Scanner(System.in);
    private static final String INDENT = "    ";

    public static void main(String[] args) {
        showGreeting();
        run();
    }

    private static void showGreeting() {
        String logo = """
                   _      _        __  __\s
                  | |    (_)      |  \\/  |
                  | |     _  ___  | \\  / |
                  | |    | |/ __| | |\\/| |
                  | |____| | (__  | |  | |
                  |______|_|\\___| |_|  |_|
                """;

        System.out.println("Hello from\n" + logo);
        printLine();
        System.out.println(INDENT + "Hello! I'm LicM");
        System.out.println(INDENT + "What can I do for you?");
        printLine();
    }

    private static void run() {
        while (true) {
            System.out.print(INDENT + "> ");
            String input = scanner.nextLine().trim();

            try {
                if (input.equals("bye")) {
                    showGoodbye();
                    break;
                } else if (input.equals("list")) {
                    showList();
                } else if (input.startsWith("mark ")) {
                    handleMark(input, true);
                } else if (input.startsWith("unmark ")) {
                    handleMark(input, false);
                } else if (input.startsWith("todo")) {
                    handleTodo(input);
                } else if (input.startsWith("deadline")) {
                    handleDeadline(input);
                } else if (input.startsWith("event")) {
                    handleEvent(input);
                } else if (input.startsWith("delete")) {
                    handleDelete(input);
                } else {
                    throw LicMException.unknownCommand();
                }
            } catch (LicMException e) {
                printLine();
                System.out.println(INDENT + e.getMessage());
                printLine();
            }
        }
        scanner.close();
    }

    private static void addTask(Task task) {
        if (taskCount < MAX_TASKS) {
            tasks[taskCount] = task;
            taskCount++;

            printLine();
            System.out.println(INDENT + "Got it. I've added this task:");
            System.out.println(INDENT + "  " + task);
            System.out.println(INDENT + "Now you have " + taskCount + " tasks in the list.");
            printLine();
        }
    }

    private static void showList() throws LicMException {
        printLine();
        if (taskCount == 0) {
            throw LicMException.emptyTaskList();
        } else {
            System.out.println(INDENT + "Here are the tasks in your list:");
            for (int i = 0; i < taskCount; i++) {
                System.out.println(INDENT + (i + 1) + ". " + tasks[i]);
            }
        }
        printLine();
    }

    private static void handleTodo(String input) throws LicMException {
        if (input.length() <= 5) {
            throw LicMException.emptyDescription("todo");
        }

        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw LicMException.emptyDescription("todo");
        }

        addTask(new Todo(description));
    }

    private static void handleDelete(String input) throws LicMException {
        if (input.length() <= 7) {
            throw LicMException.emptyDescription("delete");
        }

        String description = input.substring(7).trim();
        if (description.isEmpty()) {
            throw LicMException.emptyDescription("delete");
        }

        try {
            int taskIndex = Integer.parseInt(description) - 1;

            if (taskIndex < 0 || taskIndex >= taskCount) {
                throw LicMException.invalidTaskNumber(taskCount);
            }

            Task removedTask = tasks[taskIndex];

            // Shift remaining tasks left
            for (int i = taskIndex; i < taskCount - 1; i++) {
                tasks[i] = tasks[i + 1];
            }
            tasks[taskCount - 1] = null;
            taskCount--;

            printLine();
            System.out.println(INDENT + "Noted. I've removed this task:");
            System.out.println(INDENT + "  " + removedTask);
            System.out.println(INDENT + "Now you have " + taskCount + " tasks in the list.");
            printLine();

        } catch (NumberFormatException e) {
            throw LicMException.invalidTaskNumber(taskCount);
        }
    }

    private static void handleDeadline(String input) throws LicMException {
        if (input.length() <= 9) {
            throw LicMException.emptyDescription("deadline");
        }

        String content = input.substring(9).trim();

        if (!content.contains("/by")) {
            throw LicMException.missingParameter("deadline", "/by");
        }

        int byIndex = content.indexOf("/by");
        String description = content.substring(0, byIndex).trim();

        if (description.isEmpty()) {
            throw LicMException.emptyDescription("deadline");
        }

        String by = content.substring(byIndex + 3).trim();

        if (by.isEmpty()) {
            throw LicMException.emptyDescription("deadline");
        }

        addTask(new Deadline(description, by));
    }

    private static void handleEvent(String input) throws LicMException {
        if (input.length() <= 6) {
            throw LicMException.emptyDescription("event");
        }

        String content = input.substring(6).trim();

        if (!content.contains("/from")) {
            throw LicMException.missingParameter("event", "/from");
        }

        if (!content.contains("/to")) {
            throw LicMException.missingParameter("event", "/to");
        }

        int fromIndex = content.indexOf("/from");
        int toIndex = content.indexOf("/to");

        if (fromIndex > toIndex) {
            throw LicMException.missingParameter("event", "/from");
        }

        String description = content.substring(0, fromIndex).trim();
        if (description.isEmpty()) {
            throw LicMException.emptyDescription("event");
        }

        String from = content.substring(fromIndex + 5, toIndex).trim();
        String to = content.substring(toIndex + 3).trim();

        if (from.isEmpty()) {
            throw LicMException.emptyDescription("event");
        }

        if (to.isEmpty()) {
            throw LicMException.emptyDescription("event");
        }

        addTask(new Event(description, from, to));
    }
    private static void handleMark(String input, boolean markAsDone) throws LicMException {
        try {
            int prefixLength = markAsDone ? 5 : 7;

            if (input.length() <= prefixLength) {
                throw LicMException.missingTaskNumber();
            }

            String numberStr = input.substring(prefixLength).trim();
            if (numberStr.isEmpty()) {
                throw LicMException.missingTaskNumber();
            }

            int taskIndex = Integer.parseInt(numberStr) - 1;

            if (taskIndex < 0 || taskIndex >= taskCount) {
                throw LicMException.invalidTaskNumber(taskCount);
            }

            Task task = tasks[taskIndex];

            printLine();
            if (markAsDone) {
                task.markAsDone();
                System.out.println(INDENT + "Nice! I've marked this task as done:");
            } else {
                task.markAsNotDone();
                System.out.println(INDENT + "OK, I've marked this task as not done yet:");
            }
            System.out.println(INDENT + "  " + task);
            printLine();

        } catch (NumberFormatException e) {
            throw LicMException.invalidTaskNumber(taskCount);
        }
    }


    private static void showGoodbye() {
        printLine();
        System.out.println(INDENT + "Bye. Hope to see you again soon!");
        printLine();
    }

    private static void printLine() {
        System.out.println(INDENT + "____________________________________________________________");
    }
}