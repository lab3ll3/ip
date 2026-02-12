package licm.task;

public class TaskList {
    private static final int MAX_TASKS = 100;
    private Task[] tasks;
    private int taskCount;

    public TaskList() {
        this.tasks = new Task[MAX_TASKS];
        this.taskCount = 0;
    }

    public void addTask(Task task) {
        if (taskCount < MAX_TASKS) {
            tasks[taskCount] = task;
            taskCount++;
        } else {
            throw new IllegalStateException("Cannot add more than " + MAX_TASKS + " tasks");
        }
    }

    public Task getTask(int index) {
        if (index < 0 || index >= taskCount) {
            throw new IndexOutOfBoundsException("Invalid licm.task index: " + index);
        }
        return tasks[index];
    }

    public void markTask(int index, boolean isDone) {
        Task task = getTask(index);
        if (isDone) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }
    }

    public int size() {
        return taskCount;
    }

    public boolean isEmpty() {
        return taskCount == 0;
    }

    public Task[] getAllTasks() {
        Task[] copy = new Task[taskCount];
        System.arraycopy(tasks, 0, copy, 0, taskCount);
        return copy;
    }
}