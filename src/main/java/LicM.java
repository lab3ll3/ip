public class LicM {
    public static void main(String[] args) {
        showGreeting();
        showGoodbye();
    }

    private static void showGreeting() {
        String logo =
                "   _      _        __  __ \n" +
                        "  | |    (_)      |  \\/  |\n" +
                        "  | |     _  ___  | \\  / |\n" +
                        "  | |    | |/ __| | |\\/| |\n" +
                        "  | |____| | (__  | |  | |\n" +
                        "  |______|_|\\___| |_|  |_|\n";

        System.out.println("Hello from\n" + logo);

        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm LicM");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    private static void showGoodbye() {
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}
