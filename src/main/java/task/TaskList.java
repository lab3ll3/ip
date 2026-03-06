package task;

import java.util.ArrayList;

/**
 * Represents a list of tasks with operations to add, remove, and modify tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with an existing list of tasks.
     *
     * @param tasks The initial list of tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Gets a task at the specified index.
     *
     * @param index The index of the task (0-based)
     * @return The task at the specified index
     * @throws IndexOutOfBoundsException If the index is out of range
     */
    public Task getTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Invalid task index: " + index);
        }
        return tasks.get(index);
    }

    /**
     * Marks or unmarks a task at the specified index.
     *
     * @param index The index of the task (0-based)
     * @param isDone true to mark as done, false to mark as not done
     * @throws IndexOutOfBoundsException If the index is out of range
     */
    public void markTask(int index, boolean isDone) {
        Task task = getTask(index);
        if (isDone) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }
    }

    /**
     * Deletes a task at the specified index.
     *
     * @param index The index of the task to delete (0-based)
     * @return The deleted task
     * @throws IndexOutOfBoundsException If the index is out of range
     */
    public Task deleteTask(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Invalid task index: " + index);
        }
        return tasks.remove(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The number of tasks
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Checks if the task list is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns a copy of all tasks in the list.
     *
     * @return An ArrayList containing all tasks
     */
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }
}