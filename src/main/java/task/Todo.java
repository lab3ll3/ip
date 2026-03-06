package task;

/**
 * Represents a todo task without any date/time.
 */
public class Todo extends Task {

    /**
     * Constructs a new Todo task.
     *
     * @param description The description of the todo task
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the todo task.
     *
     * @return A string in the format "[T][status] description"
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}