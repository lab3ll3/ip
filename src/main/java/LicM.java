import java.util.Scanner;

public class LicM {
    private static final int MAX_TASKS = 100;
    private static final String[] tasks = new String[MAX_TASKS];
    private static int taskCount = 0;

    public static void main(String[] args) {
        LicMChat();
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

    private static void showGoodbye() {
        System.out.println("    Bye. Hope to see you again soon!");
        printLine();
    }

    private static void printLine() {
        System.out.println("    ____________________________________________________________");
    }
}
