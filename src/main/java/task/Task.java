package task;

/**
 * Represents an abstract task with a description and completion status.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new Task with the given description.
     *
     * @param description The description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns the status icon representing whether the task is done.
     *
     * @return "X" if done, otherwise a space " "
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Returns the description of the task.
     *
     * @return The task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns whether the task is done.
     *
     * @return true if done, false otherwise
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns a string representation of the task.
     *
     * @return A string in the format "[status] description"
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}