//@@author Hailinx
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.util.TimeConvertUtil.convertTimeToString;

import java.time.LocalDateTime;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a TodoItem in the address book.
 */
public class TodoItem implements Comparable<TodoItem> {

    public static final String MESSAGE_TODOITEM_CONSTRAINTS =
            "The end time should be later than start time. Task cannot be empty.";

    public final LocalDateTime start;
    public final LocalDateTime end;
    public final String task;

    /**
     * Validates given time and task.
     * @param start : the start time of the task, cannot be null
     * @param end : the end time of the task, can be null
     * @param task : task in string, cannot be null
     */
    public TodoItem(LocalDateTime start, LocalDateTime end, String task)
            throws IllegalValueException {
        requireNonNull(start, task);
        if (!isValidTodoItem(start, end, task.trim())) {
            throw new IllegalValueException(MESSAGE_TODOITEM_CONSTRAINTS);
        }
        this.start = start;
        this.end = end;
        this.task = task.trim();
    }

    /**
     * Checks whether the inputs are valid.
     */
    private boolean isValidTodoItem(LocalDateTime start, LocalDateTime end, String task) {
        return !(task.isEmpty() || end != null && end.compareTo(start) < 0);
    }

    /**
     * @return formatted time string
     */
    public String getTimeString() {
        String timeStr;
        String startTimeStr = convertTimeToString(start);
        if (end != null) {
            String endTime = convertTimeToString(end);
            timeStr = "From: " + startTimeStr + "   To: " + endTime;
        } else {
            timeStr = "From: " + startTimeStr;
        }
        return timeStr;
    }

    @Override
    public String toString() {
        return "From:" + convertTimeToString(start)
                + " To:" + convertTimeToString(end)
                + " Task:" + task;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TodoItem // instanceof handles nulls
                && this.toString().equals(other.toString())); // state check
    }

    @Override
    public int compareTo(TodoItem other) {
        return this.start.compareTo(other.start);
    }
}
