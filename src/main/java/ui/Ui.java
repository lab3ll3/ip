package ui;

import java.util.Scanner;

import exceptions.LicMException;
import task.Task;
import java.util.ArrayList;

/**
 * Handles user interface interactions including input and output.
 */
public class Ui {
    public static final String INDENT = "    ";
    private static final String LINE = INDENT + "____________________________________________________________";
    private final Scanner scanner;

    /**
     * Constructs a new Ui object with a Scanner for user input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message and logo.
     */
    public void showGreeting() {
        String logo = """
                   _      _        __  __\s
                  | |    (_)      |  \\/  |
                  | |     _  ___  | \\  / |
                  | |    | |/ __| | |\\/| |
                  | |____| | (__  | |  | |
                  |______|_|\\___| |_|  |_|
                """;

        System.out.println("Hello from\n" + logo);
        showLine();
        System.out.println(INDENT + "Hello! I'm LicM");
        System.out.println(INDENT + "What can I do for you?");
        showLine();
    }

    /**
     * Displays the goodbye message.
     */
    public void showGoodbye() {
        showLine();
        System.out.println(INDENT + "Bye. Hope to see you again soon!");
        showLine();
    }

    /**
     * Displays a divider line.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Displays a simple message.
     *
     * @param message The message to display
     */
    public void showMessage(String message) {
        System.out.println(INDENT + message);
    }

    /**
     * Displays a message confirming a task was added.
     *
     * @param task The task that was added
     * @param size The new size of the task list
     */
    public void showTaskAdded(Task task, int size) {
        showLine();
        System.out.println(INDENT + "Got it. I've added this task:");
        System.out.println(INDENT + "  " + task);
        System.out.println(INDENT + "Now you have " + size + " tasks in the list.");
        showLine();
    }

    /**
     * Displays a message confirming a task was marked or unmarked.
     *
     * @param task The task that was marked
     * @param isDone true if marked as done, false if marked as not done
     */
    public void showTaskMarked(Task task, boolean isDone) {
        showLine();
        if (isDone) {
            System.out.println(INDENT + "Nice! I've marked this task as done:");
        } else {
            System.out.println(INDENT + "OK, I've marked this task as not done yet:");
        }
        System.out.println(INDENT + "  " + task);
        showLine();
    }

    /**
     * Displays a message confirming a task was deleted.
     *
     * @param task The task that was deleted
     * @param size The new size of the task list
     */
    public void showTaskDeleted(Task task, int size) {
        showLine();
        System.out.println(INDENT + "Noted. I've removed this task:");
        System.out.println(INDENT + "  " + task);
        System.out.println(INDENT + "Now you have " + size + " tasks in the list.");
        showLine();
    }

    /**
     * Displays the list of tasks.
     *
     * @param tasks The list of tasks to display
     * @throws LicMException If the task list is empty
     */
    public void showTaskList(ArrayList<Task> tasks) throws LicMException {
        showLine();
        if (tasks.isEmpty()) {
            throw LicMException.emptyTaskList();
        } else {
            System.out.println(INDENT + "Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(INDENT + (i + 1) + ". " + tasks.get(i));
            }
        }
        showLine();
    }

    /**
     * Displays an error message.
     *
     * @param error The error message to display
     */
    public void showError(String error) {
        showLine();
        System.out.println(INDENT + error);
        showLine();
    }

    /**
     * Displays a message when loading tasks fails.
     */
    public void showLoadingError() {
        showMessage("No existing tasks found. Starting fresh!");
    }

    /**
     * Reads a command from the user.
     *
     * @return The trimmed user input
     */
    public String readCommand() {
        System.out.print(INDENT + "> ");
        return scanner.nextLine().trim();
    }

    /**
     * Closes the scanner.
     */
    public void close() {
        scanner.close();
    }
}