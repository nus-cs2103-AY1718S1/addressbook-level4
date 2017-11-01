package seedu.address.model.person;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.util.TimeConvertUtil.convertTimeToString;

public class TodoItem implements Comparable<TodoItem>{

    public final LocalDateTime start;
    public final LocalDateTime end;
    public final String task;

    /**
     * Validates given time and task.
     * @param start : the start time of the task, cannot be null
     * @param end : the end time of the task, can be null
     * @param task : task in string, cannot be null
     */
    public TodoItem(LocalDateTime start, LocalDateTime end, String task) {
        requireNonNull(start, task);
        this.start = start;
        this.end = end;
        this.task = task.trim();
    }

    @Override
    public String toString() {
        return "From:" + convertTimeToString(start)
                + "\tTo:"+convertTimeToString(end)
                + "\t" + task;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TodoItem // instanceof handles nulls
                && this.toString().equals(other.toString())); // state check
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public int compareTo(TodoItem other) {
        return this.start.compareTo(other.start);
    }
}
