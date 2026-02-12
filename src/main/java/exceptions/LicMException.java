public class LicMException extends Exception {

    public LicMException(String message) {
        super(message);
    }

    public static LicMException emptyDescription(String commandType) {
        return new LicMException(
                "Oopsies!!! The description of a " + commandType + " cannot be empty."
        );
    }

    public static LicMException missingParameter(String commandType, String parameter) {
        return new LicMException(
                "Oopsies!!! " + commandType + " must include '" + parameter + "'."
        );
    }

    public static LicMException unknownCommand() {
        return new LicMException(
                "I'm sorry, but I don't know what that means DDDD:"
        );
    }

    public static LicMException invalidTaskNumber(int maxTasks) {
        return new LicMException(
                "Oopsies!!! Invalid task number. Please enter a number between 1 and " + maxTasks + "."
        );
    }

    public static LicMException missingTaskNumber() {
        return new LicMException(
                "Oopsies!!! Please specify a task number."
        );
    }

    public static LicMException emptyTaskList() {
        return new LicMException(
                "Oopsies!!! Your task list is empty. Add some tasks first!"
        );
    }
}
