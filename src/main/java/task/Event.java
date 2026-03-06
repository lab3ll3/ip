package task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with start and end dates.
 */
public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

    /**
     * Constructs a new Event task.
     *
     * @param description The description of the event
     * @param from The start date in yyyy-mm-dd format
     * @param to The end date in yyyy-mm-dd format
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = LocalDate.parse(from);
        this.to = LocalDate.parse(to);
    }

    /**
     * Returns the start date of the event.
     *
     * @return The start date as a LocalDate
     */
    public LocalDate getFrom() {
        return from;
    }

    /**
     * Returns the end date of the event.
     *
     * @return The end date as a LocalDate
     */
    public LocalDate getTo() {
        return to;
    }

    /**
     * Returns a string representation of the event task.
     *
     * @return A string in the format "[E][status] description (from: MMM d yyyy to: MMM d yyyy)"
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy");
        String formattedFrom = from.format(formatter);
        String formattedTo = to.format(formatter);
        return "[E]" + super.toString() + " (from: " + formattedFrom + " to: " + formattedTo + ")";
    }
}