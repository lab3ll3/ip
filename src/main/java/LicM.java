import exceptions.LicMException;
import task.Task;
import storage.Storage;
import task.TaskList;
import ui.Ui;
import parser.Parser;
import parser.Parser.ParsedCommand;
import java.util.ArrayList;

/**
 * Main class for the LicM task management application.
 * Initializes components and runs the main interaction loop.
 */
public class LicM {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a new LicM application instance.
     *
     * @param filePath The path to the data file for storing tasks
     */
    public LicM(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (LicMException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main application loop, reading and processing user commands.
     */
    public void run() {
        ui.showGreeting();

        while (true) {
            try {
                String input = ui.readCommand();
                ParsedCommand parsed = Parser.parse(input);

                switch (parsed.type) {
                    case BYE:
                        ui.showGoodbye();
                        ui.close();
                        return;

                    case LIST:
                        if (tasks.isEmpty()) {
                            throw LicMException.emptyTaskList();
                        }
                        ui.showTaskList(tasks.getAllTasks());
                        break;

                    case MARK:
                        handleMark(parsed.taskIndex, true);
                        break;

                    case UNMARK:
                        handleMark(parsed.taskIndex, false);
                        break;

                    case TODO:
                        handleAdd(Parser.parseTodo(parsed.arguments));
                        break;

                    case DEADLINE:
                        handleAdd(Parser.parseDeadline(parsed.arguments));
                        break;

                    case EVENT:
                        handleAdd(Parser.parseEvent(parsed.arguments));
                        break;

                    case DELETE:
                        handleDelete(parsed.taskIndex);
                        break;

                    case FIND:
                        handleFind(parsed.arguments);
                        break;

                    default:
                        throw LicMException.unknownCommand();
                }

            } catch (LicMException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * Handles adding a new task to the task list.
     *
     * @param task The task to be added
     * @throws LicMException If there is an error saving the task
     */
    private void handleAdd(Task task) throws LicMException {
        tasks.addTask(task);
        storage.save(tasks.getAllTasks());
        ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Handles marking or unmarking a task as done.
     *
     * @param taskIndex The index of the task to mark (0-based)
     * @param markAsDone true to mark as done, false to mark as not done
     * @throws LicMException If the task index is invalid or there is a save error
     */
    private void handleMark(int taskIndex, boolean markAsDone) throws LicMException {
        if (tasks.isEmpty()) {
            throw LicMException.emptyTaskList();
        }

        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw LicMException.invalidTaskNumber(tasks.size());
        }

        tasks.markTask(taskIndex, markAsDone);
        storage.save(tasks.getAllTasks());
        ui.showTaskMarked(tasks.getTask(taskIndex), markAsDone);
    }

    /**
     * Handles deleting a task from the task list.
     *
     * @param taskIndex The index of the task to delete (0-based)
     * @throws LicMException If the task index is invalid or there is a save error
     */
    private void handleDelete(int taskIndex) throws LicMException {
        if (taskIndex < 0 || taskIndex >= tasks.size()) {
            throw LicMException.invalidTaskNumber(tasks.size());
        }

        Task removedTask = tasks.deleteTask(taskIndex);
        storage.save(tasks.getAllTasks());
        ui.showTaskDeleted(removedTask, tasks.size());
    }

    /**
     * Handles finding tasks by keyword in their descriptions.
     *
     * @param input The user input containing the search keyword
     * @throws LicMException If the keyword is empty or invalid
     */
    private void handleFind(String input) throws LicMException {
        if (input.length() <= 5) {
            throw new LicMException("Oopsies!!! Please specify a keyword to find.");
        }

        String keyword = input.substring(5).trim().toLowerCase();
        if (keyword.isEmpty()) {
            throw new LicMException("Oopsies!!! Please specify a keyword to find.");
        }

        ArrayList<Task> matchingTasks = new ArrayList<>();

        // Search through all tasks for the keyword
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.getTask(i);
            if (task.getDescription().toLowerCase().contains(keyword)) {
                matchingTasks.add(task);
            }
        }

        ui.showLine();
        if (matchingTasks.isEmpty()) {
            System.out.println(ui.INDENT + "No matching tasks found for: " + keyword);
        } else {
            System.out.println(ui.INDENT + "Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println(ui.INDENT + (i + 1) + ". " + matchingTasks.get(i));
            }
        }
        ui.showLine();
    }

    /**
     * The main entry point of the LicM application.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        new LicM("./data/licm.txt").run();
    }
}