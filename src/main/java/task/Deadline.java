package task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task with a due date.
 */
public class Deadline extends Task {
    private final LocalDate by;

    /**
     * Constructs a new Deadline task.
     *
     * @param description The description of the deadline task
     * @param by The due date in yyyy-mm-dd format
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDate.parse(by);
    }

    /**
     * Returns the due date of the deadline.
     *
     * @return The due date as a LocalDate
     */
    public LocalDate getBy() {
        return by;
    }

    /**
     * Returns a string representation of the deadline task.
     *
     * @return A string in the format "[D][status] description (by: MMM d yyyy)"
     */
    @Override
    public String toString() {
        String formattedDate = by.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        return "[D]" + super.toString() + " (by: " + formattedDate + ")";
    }
}