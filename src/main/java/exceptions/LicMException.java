package exceptions;

/**
 * Custom exception class for LicM application-specific errors.
 */
public class LicMException extends Exception {

    /**
     * Constructs a new LicMException with the specified message.
     *
     * @param message The error message
     */
    public LicMException(String message) {
        super(message);
    }

    /**
     * Creates an exception for empty task description.
     *
     * @param commandType The type of command that had empty description
     * @return A LicMException with appropriate message
     */
    public static LicMException emptyDescription(String commandType) {
        return new LicMException(
                "Oopsies!!! The description of a " + commandType + " cannot be empty."
        );
    }

    /**
     * Creates an exception for missing command parameter.
     *
     * @param commandType The type of command missing a parameter
     * @param parameter The missing parameter
     * @return A LicMException with appropriate message
     */
    public static LicMException missingParameter(String commandType, String parameter) {
        return new LicMException(
                "Oopsies!!! " + commandType + " must include '" + parameter + "'."
        );
    }

    /**
     * Creates an exception for unknown command.
     *
     * @return A LicMException with appropriate message
     */
    public static LicMException unknownCommand() {
        return new LicMException(
                "I'm sorry, but I don't know what that means DDDD:"
        );
    }

    /**
     * Creates an exception for invalid task number.
     *
     * @param maxTasks The maximum valid task number
     * @return A LicMException with appropriate message
     */
    public static LicMException invalidTaskNumber(int maxTasks) {
        return new LicMException(
                "Oopsies!!! Invalid task number. Please enter a number between 1 and " + maxTasks + "."
        );
    }

    /**
     * Creates an exception for missing task number.
     *
     * @return A LicMException with appropriate message
     */
    public static LicMException missingTaskNumber() {
        return new LicMException(
                "Oopsies!!! Please specify a task number."
        );
    }

    /**
     * Creates an exception for empty task list.
     *
     * @return A LicMException with appropriate message
     */
    public static LicMException emptyTaskList() {
        return new LicMException(
                "Oopsies!!! Your task list is empty. Add some tasks first!"
        );
    }
}