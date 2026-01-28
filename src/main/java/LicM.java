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
            String input = scanner.nextLine().trim();

            if (input.equals("bye")) {
                showGoodbye();
                scanner.close();
                return;
            } else if (input.equals("list")) {
                showList();
            } else if (!input.isEmpty()) {
                showAdd(input);
            } else {
                showEcho(input);
            }
        }
    }

    private static void showEcho(String input) {
        printLine();
        System.out.print(input);
        printLine();
    }

    private static void showAdd(String input) {
        if (taskCount < MAX_TASKS) {
            tasks[taskCount] = input;
            taskCount++;

            printLine();
            System.out.println("    added: " + input);
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
                System.out.println("    " + (i + 1) + ". " + tasks[i]);
            }
        }
        printLine();
    }

    public static void showEcho() {
        showGreeting();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            System.out.println("____________________________________________________________");
            System.out.println(input);
            System.out.println("____________________________________________________________");

            if (input.equals("bye")) {
                showGoodbye();
                scanner.close();
                return;
            }
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
