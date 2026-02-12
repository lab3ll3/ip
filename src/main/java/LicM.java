package licm;

import licm.task.*;
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
            } else {
                printLine();
                System.out.println(INDENT + "☹ OOPS!!! I'm sorry, I don't know what that means :-(");
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

    private static void showList() {
        printLine();
        if (taskCount == 0) {
            System.out.println(INDENT + "Your task list is empty.");
        } else {
            System.out.println(INDENT + "Here are the tasks in your list:");
            for (int i = 0; i < taskCount; i++) {
                System.out.println(INDENT + (i + 1) + ". " + tasks[i]);
            }
        }
        printLine();
    }

    private static void handleTodo(String input) {
        if (input.length() <= 5) {
            printLine();
            System.out.println(INDENT + "☹ OOPS!!! The description of a todo cannot be empty.");
            printLine();
            return;
        }

        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            printLine();
            System.out.println(INDENT + "☹ OOPS!!! The description of a todo cannot be empty.");
            printLine();
            return;
        }

        addTask(new Todo(description));
    }

    private static void handleDeadline(String input) {
        if (input.length() <= 9) {
            printLine();
            System.out.println(INDENT + "☹ OOPS!!! The description of a deadline cannot be empty.");
            printLine();
            return;
        }

        String content = input.substring(9).trim();
        int byIndex = content.indexOf("/by ");

        if (byIndex == -1) {
            printLine();
            System.out.println(INDENT + "☹ OOPS!!! Deadline must include '/by' time.");
            printLine();
            return;
        }

        String description = content.substring(0, byIndex).trim();
        String by = content.substring(byIndex + 4).trim();

        if (description.isEmpty()) {
            printLine();
            System.out.println(INDENT + "☹ OOPS!!! The description of a deadline cannot be empty.");
            printLine();
            return;
        }

        addTask(new Deadline(description, by));
    }

    private static void handleEvent(String input) {
        if (input.length() <= 6) {
            printLine();
            System.out.println(INDENT + "☹ OOPS!!! The description of an event cannot be empty.");
            printLine();
            return;
        }

        String content = input.substring(6).trim();
        int fromIndex = content.indexOf("/from ");
        int toIndex = content.indexOf("/to ");

        if (fromIndex == -1 || toIndex == -1) {
            printLine();
            System.out.println(INDENT + "☹ OOPS!!! Event must include '/from' and '/to' times.");
            printLine();
            return;
        }

        String description = content.substring(0, fromIndex).trim();
        String from = content.substring(fromIndex + 6, toIndex).trim();
        String to = content.substring(toIndex + 4).trim();

        if (description.isEmpty()) {
            printLine();
            System.out.println(INDENT + "☹ OOPS!!! The description of an event cannot be empty.");
            printLine();
            return;
        }

        addTask(new Event(description, from, to));
    }

    private static void handleMark(String input, boolean markAsDone) {
        try {
            int prefixLength = markAsDone ? 5 : 7;
            String numberStr = input.substring(prefixLength).trim();
            int taskIndex = Integer.parseInt(numberStr) - 1;

            if (taskIndex < 0 || taskIndex >= taskCount) {
                printLine();
                System.out.println(INDENT + "☹ OOPS!!! Task number " + (taskIndex + 1) + " does not exist.");
                printLine();
                return;
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

        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            printLine();
            System.out.println(INDENT + "☹ OOPS!!! Please use: " + (markAsDone ? "mark" : "unmark") + " <task-number>");
            printLine();
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