package ui;

import java.util.Scanner;

import exceptions.LicMException;
import task.Task;
import java.util.ArrayList;


public class Ui {
    public static final String INDENT = "    ";
    private static final String LINE = INDENT + "____________________________________________________________";
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

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

    public void showGoodbye() {
        showLine();
        System.out.println(INDENT + "Bye. Hope to see you again soon!");
        showLine();
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public void showMessage(String message) {
        System.out.println(INDENT + message);
    }

    public void showTaskAdded(Task task, int size) {
        showLine();
        System.out.println(INDENT + "Got it. I've added this task:");
        System.out.println(INDENT + "  " + task);
        System.out.println(INDENT + "Now you have " + size + " tasks in the list.");
        showLine();
    }

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

    public void showTaskDeleted(Task task, int size) {
        showLine();
        System.out.println(INDENT + "Noted. I've removed this task:");
        System.out.println(INDENT + "  " + task);
        System.out.println(INDENT + "Now you have " + size + " tasks in the list.");
        showLine();
    }

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

    public void showError(String error) {
        showLine();
        System.out.println(INDENT + error);
        showLine();
    }

    public void showLoadingError() {
        showMessage("No existing tasks found. Starting fresh!");
    }

    public String readCommand() {
        System.out.print(INDENT + "> ");
        return scanner.nextLine().trim();
    }

    public void close() {
        scanner.close();
    }
}