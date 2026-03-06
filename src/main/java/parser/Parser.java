package parser;

import exceptions.LicMException;
import task.Deadline;
import task.Event;
import task.Task;
import task.Todo;

import java.time.LocalDate;

/**
 * Parses user input commands into executable commands.
 */
public class Parser {

    /**
     * Enum representing the different types of commands.
     */
    public enum CommandType {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND, UNKNOWN
    }

    /**
     * Represents a parsed command with its type and associated data.
     */
    public static class ParsedCommand {
        public CommandType type;
        public String arguments;
        public int taskIndex;

        /**
         * Constructs a parsed command with no additional data.
         *
         * @param type The type of command
         */
        public ParsedCommand(CommandType type) {
            this.type = type;
        }

        /**
         * Constructs a parsed command with string arguments.
         *
         * @param type The type of command
         * @param arguments The command arguments as a string
         */
        public ParsedCommand(CommandType type, String arguments) {
            this.type = type;
            this.arguments = arguments;
        }

        /**
         * Constructs a parsed command with a task index.
         *
         * @param type The type of command
         * @param taskIndex The task index (0-based)
         */
        public ParsedCommand(CommandType type, int taskIndex) {
            this.type = type;
            this.taskIndex = taskIndex;
        }
    }

    /**
     * Parses a full user input string into a ParsedCommand object.
     *
     * @param input The raw user input string
     * @return A ParsedCommand object containing the command type and data
     * @throws LicMException If the command is unknown or has invalid format
     */
    public static ParsedCommand parse(String input) throws LicMException {
        if (input.equals("bye")) {
            return new ParsedCommand(CommandType.BYE);
        } else if (input.equals("list")) {
            return new ParsedCommand(CommandType.LIST);
        } else if (input.startsWith("find ")) {
            return new ParsedCommand(CommandType.FIND, input);
        } else if (input.startsWith("mark ")) {
            return parseMarkUnmark(input, CommandType.MARK);
        } else if (input.startsWith("unmark ")) {
            return parseMarkUnmark(input, CommandType.UNMARK);
        } else if (input.startsWith("todo")) {
            return new ParsedCommand(CommandType.TODO, input);
        } else if (input.startsWith("deadline")) {
            return new ParsedCommand(CommandType.DEADLINE, input);
        } else if (input.startsWith("event")) {
            return new ParsedCommand(CommandType.EVENT, input);
        } else if (input.startsWith("delete")) {
            return parseDelete(input);
        } else {
            throw LicMException.unknownCommand();
        }
    }

    /**
     * Parses mark and unmark commands to extract the task index.
     *
     * @param input The raw user input
     * @param type The command type (MARK or UNMARK)
     * @return A ParsedCommand with the task index
     * @throws LicMException If the task number is missing or invalid
     */
    private static ParsedCommand parseMarkUnmark(String input, CommandType type) throws LicMException {
        int prefixLength = (type == CommandType.MARK) ? 5 : 7;

        if (input.length() <= prefixLength) {
            throw LicMException.missingTaskNumber();
        }

        String numberStr = input.substring(prefixLength).trim();
        if (numberStr.isEmpty()) {
            throw LicMException.missingTaskNumber();
        }

        try {
            int taskIndex = Integer.parseInt(numberStr) - 1;
            return new ParsedCommand(type, taskIndex);
        } catch (NumberFormatException e) {
            throw LicMException.invalidTaskNumber(0);
        }
    }

    /**
     * Parses delete commands to extract the task index.
     *
     * @param input The raw user input
     * @return A ParsedCommand with the task index
     * @throws LicMException If the task number is missing or invalid
     */
    private static ParsedCommand parseDelete(String input) throws LicMException {
        if (input.length() <= 7) {
            throw LicMException.emptyDescription("delete");
        }

        String numberStr = input.substring(7).trim();
        if (numberStr.isEmpty()) {
            throw LicMException.emptyDescription("delete");
        }

        try {
            int taskIndex = Integer.parseInt(numberStr) - 1;
            return new ParsedCommand(CommandType.DELETE, taskIndex);
        } catch (NumberFormatException e) {
            throw LicMException.invalidTaskNumber(0);
        }
    }

    /**
     * Parses a todo command and creates a Todo task.
     *
     * @param input The raw user input
     * @return A new Todo task
     * @throws LicMException If the description is empty
     */
    public static Task parseTodo(String input) throws LicMException {
        if (input.length() <= 5) {
            throw LicMException.emptyDescription("todo");
        }

        String description = input.substring(5).trim();
        if (description.isEmpty()) {
            throw LicMException.emptyDescription("todo");
        }

        return new Todo(description);
    }

    /**
     * Parses a deadline command and creates a Deadline task.
     *
     * @param input The raw user input in format "deadline <description> /by <date>"
     * @return A new Deadline task
     * @throws LicMException If format is invalid, description empty, or date invalid
     */
    public static Task parseDeadline(String input) throws LicMException {
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

        // Validate date format
        try {
            LocalDate.parse(by);
        } catch (java.time.format.DateTimeParseException e) {
            throw new LicMException("Oopsies!!! Invalid date format. Please use yyyy-mm-dd");
        }

        return new Deadline(description, by);
    }

    /**
     * Parses an event command and creates an Event task.
     *
     * @param input The raw user input in format "event <description> /from <date> /to <date>"
     * @return A new Event task
     * @throws LicMException If format is invalid, description empty, or dates invalid
     */
    public static Task parseEvent(String input) throws LicMException {
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

        // Validate date formats
        try {
            LocalDate.parse(from);
            LocalDate.parse(to);
        } catch (java.time.format.DateTimeParseException e) {
            throw new LicMException("Oopsies!!! Invalid date format. Please use yyyy-mm-dd");
        }

        return new Event(description, from, to);
    }
}