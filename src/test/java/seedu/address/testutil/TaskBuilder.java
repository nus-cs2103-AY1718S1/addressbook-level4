package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Description;
import seedu.address.model.task.DueDate;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;

//@@author zhangshuoyang
/**
 * A utility class to help with buidling Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_DESCRIPTION = "CS2103T Homework";
    public static final String DEFAULT_PRIORITY = "2";
    public static final String DEFAULT_DUEDATE = "25/10/2017";

    private Task task;

    public TaskBuilder() {
        try {
            Description description = new Description(DEFAULT_DESCRIPTION);
            Priority priority = new Priority(DEFAULT_PRIORITY);
            DueDate dueDate = new DueDate(DEFAULT_DUEDATE);
            this.task = new Task(description, priority, dueDate);
        } catch (IllegalValueException e) {
            throw new AssertionError("The value of default task is invalid.");
        }
    }

    /**
     * Initializes the TaskBuider with the data of {@code taskToCopy}.
     */
    public TaskBuilder(ReadOnlyTask taskToCopy) {
        this.task = new Task(taskToCopy);
    }

    /**
     * Sets the {@code Description} of the {@code Task} that we are buidling.
     */
    public TaskBuilder withDescription(String description) {
        try {
            this.task.setDescription(new Description(description));
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException("invalid name");
        }
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Task} that we are buidling.
     */
    public TaskBuilder withPriority(String priority) {
        try {
            this.task.setPriority(new Priority(priority));
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException("invalid priority");
        }
        return this;
    }

    /**
     * Sets the {@code DueDate} of the {@code Task} that we are buidling.
     */
    public TaskBuilder withDueDate(String dueDate) {
        try {
            this.task.setDuedate(new DueDate(dueDate));
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException("invalid due date");
        }
        return this;
    }

    public Task build() {
        return this.task;
    }
}
