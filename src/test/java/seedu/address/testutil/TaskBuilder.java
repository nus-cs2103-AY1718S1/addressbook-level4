package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Header;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;

//@@author deep4k
/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {
    public static final String DEFAULT_HEADER = "Lunch meet";

    public static final LocalDateTime DEFAULT_TIME =
            LocalDateTime.of(2017, 11, 22, 10, 18);
    private Task task;

    public TaskBuilder() {
        try {
            this.task = new Task(new Header(DEFAULT_HEADER));
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default task's values are invalid.");
        }
    }

    /**
     * Initializes the PersonBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(ReadOnlyTask taskToCopy) {
        this.task = new Task(taskToCopy);
    }

    /**
     * Sets the {@code Header} of the {@code Task} that we are building.
     */
    public TaskBuilder withHeader(String header) throws IllegalValueException {
        this.task.setHeader(new Header(header));
        this.task.setLastUpdatedTime(DEFAULT_TIME);
        return this;
    }

    /**
     * Sets the {@code CompletionStatus} of the {@code Task} that we are building.
     */
    public TaskBuilder withCompletionStatus() {
        this.task.setComplete();
        this.task.setLastUpdatedTime(DEFAULT_TIME);
        return this;
    }

    /**
     * Sets the {@code IncompletionStatus} of the {@code Task} that we are building.
     */
    public TaskBuilder withIncompletionStatus() {
        this.task.setIncomplete();
        this.task.setLastUpdatedTime(DEFAULT_TIME);
        return this;
    }

    /**
     * Sets the {@code StartTime} of the {@code Task} that we are building.
     */
    public TaskBuilder withStartTime(LocalDateTime startTime) {
        this.task.setStartDateTime(Optional.ofNullable(startTime));
        this.task.setLastUpdatedTime(DEFAULT_TIME);
        return this;
    }

    /**
     * Sets the {@code EndTime} of the {@code Task} that we are building.
     */
    public TaskBuilder withEndTime(LocalDateTime endTime) {
        this.task.setEndDateTime(Optional.ofNullable(endTime));
        this.task.setLastUpdatedTime(DEFAULT_TIME);
        return this;
    }

    public Task build() {
        return this.task;
    }

}
