//@@author srishag
package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Desc;
import seedu.address.model.task.Header;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_HEADER = "Homework";
    public static final String DEFAULT_DESC = "Questions 1 and 2";
    public static final String DEFAULT_DEADLINE = "23/12/2017";

    private Task task;

    public TaskBuilder() {
        try {
            Header defaultHeader = new Header(DEFAULT_HEADER);
            Desc defaultDesc = new Desc(DEFAULT_DESC);
            Deadline defaultDeadline = new Deadline(DEFAULT_DEADLINE);

            this.task = new Task(defaultHeader, defaultDesc, defaultDeadline);

        } catch (IllegalValueException ive) {
            throw new AssertionError("Default task's values are invalid.");
        }
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(ReadOnlyTask taskToCopy) {
        this.task = new Task(taskToCopy);
    }

    /**
     * Sets the {@code Header} of the {@code Task} that we are building.
     */
    public TaskBuilder withHeader(String header) {
        try {
            this.task.setHeader(new Header(header));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("header is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Desc} of the {@code Task} that we are building.
     */
    public TaskBuilder withDesc(String desc) {
        try {
            this.task.setDesc(new Desc(desc));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("desc are expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Task} that we are building.
     */
    public TaskBuilder withDeadline(String deadline) {
        try {
            this.task.setDeadline(new Deadline(deadline));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("deadline is expected to be unique.");
        }
        return this;
    }

    public Task build() {
        return this.task;
    }

}
