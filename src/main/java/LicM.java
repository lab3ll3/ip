import java.util.Scanner;

public class LicM {
    private static final int MAX_TASKS = 100;
    private static final Task[] TASKS = new Task[MAX_TASKS];
    private static int taskCount = 0;
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        runChat();
    }

    private static class Task {
        protected String description;
        protected boolean isDone;

        public Task(String description) {
            this.description = description;
            this.isDone = false;
        }

        public void markAsDone() {
            this.isDone = true;
        }

        public void markAsNotDone() {
            this.isDone = false;
        }

        public String getStatusIcon() {
            return (isDone ? "X" : " ");
        }

        @Override
        public String toString() {
            return "[" + getStatusIcon() + "] " + description;
        }
    }

private static class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

private static class Deadline extends Task {
    private final String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}

private static class Event extends Task {
    private final String from;
    private final String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
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
        System.out.println("    Hello! I'm LicM");
        System.out.println("    What can I do for you?");
        printLine();
    }

    public static void runChat() {
        showGreeting();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = SCANNER.nextLine().trim();

            if (input.equals("bye")) {
                showGoodbye();
                scanner.close();
                return;
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
                System.out.println("    ☹ OOPS!!! I'm sorry, I don't know what that means :-(");
                printLine();
            }
        }
    }

    private static void addTask(Task task) {
        if (taskCount < MAX_TASKS) {
            Task newTask = new Task(description);
            TASKS[taskCount] = newTask;
            taskCount++;

            printLine();
            System.out.println("    added: " + description);
            printLine();
        } else {
            printLine();
            System.out.println("    I can't add any more tasks!");
            printLine();
        }
    }

    private static void showList() {
        printLine();
        if (taskCount == 0) {
            System.out.println("    Your task list is empty.");
        } else {
            for (int i = 0; i < taskCount; i++) {
                System.out.println("    " + (i + 1) + ". " + TASKS[i]);
            }
        }
        printLine();
    }

    private static void handleMark(String input, boolean markAsDone) {
        printLine();

        try {
            String numberStr = input.substring(markAsDone ? 5 : 7).trim();
            int taskIndex = Integer.parseInt(numberStr) - 1;

            if (taskIndex < 0 || taskIndex >= taskCount) {
                System.out.println("    Task number " + (taskIndex + 1) + " does not exist.");
                printLine();
                return;
            }

            Task task = TASKS[taskIndex];

            if (markAsDone) {
                task.markAsDone();
                System.out.println("    Nice! I've marked this task as done:");
            } else {
                task.markAsNotDone();
                System.out.println("    OK, I've marked this task as not done yet:");
            }

            System.out.println("      " + task);

        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            System.out.println("    Please use: " + (markAsDone ? "mark" : "unmark") + " <task-number>");
        }

        printLine();
    }

private static void handleTodo(String input) {
    if (input.length() <= "todo ".length()) {
        printLine();
        System.out.println("    ☹ OOPS!!! The description of a todo cannot be empty.");
        printLine();
        return;
    }

    String description = input.substring(5).trim();
    addTask(new Todo(description));
}

private static void handleDeadline(String input) {
    try {
        String content = input.substring(9).trim();
        int byIndex = content.indexOf("/by ");

        if (byIndex == -1) {
            printLine();
            System.out.println("    ☹ OOPS!!! Deadline must include '/by' time.");
            printLine();
            return;
        }

        String description = content.substring(0, byIndex).trim();
        String by = content.substring(byIndex + 4).trim();

        if (description.isEmpty()) {
            printLine();
            System.out.println("    ☹ OOPS!!! The description of a deadline cannot be empty.");
            printLine();
            return;
        }

        addTask(new Deadline(description, by));
    } catch (StringIndexOutOfBoundsException e) {
        printLine();
        System.out.println("    ☹ OOPS!!! Invalid deadline format. Use: deadline <task> /by <time>");
        printLine();
    }
}

private static void handleEvent(String input) {
    try {
        String content = input.substring(6).trim();
        int fromIndex = content.indexOf("/from ");
        int toIndex = content.indexOf("/to ");

        if (fromIndex == -1 || toIndex == -1) {
            printLine();
            System.out.println("    ☹ OOPS!!! Event must include '/from' and '/to' times.");
            printLine();
            return;
        }

        String description = content.substring(0, fromIndex).trim();
        String from = content.substring(fromIndex + 6, toIndex).trim();
        String to = content.substring(toIndex + 4).trim();

        if (description.isEmpty()) {
            printLine();
            System.out.println("    ☹ OOPS!!! The description of an event cannot be empty.");
            printLine();
            return;
        }

        addTask(new Event(description, from, to));
    } catch (StringIndexOutOfBoundsException e) {
        printLine();
        System.out.println("    ☹ OOPS!!! Invalid event format. Use: event <task> /from <time> /to <time>");
        printLine();
    }
}

    private static void showGoodbye() {
        System.out.println("    Bye. Hope to see you again soon!");
        printLine();
    }

    private static void printLine() {
        System.out.println("    ____________________________________________________________");
    }
}
