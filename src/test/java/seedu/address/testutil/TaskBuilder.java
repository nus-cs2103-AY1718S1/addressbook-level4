package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.SingleEventDate;
import seedu.address.model.task.StartDate;
import seedu.address.model.task.Task;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_DESCRIPTION = "CS2103T assignment due";
    public static final String DEFAULT_STARTDATE = "17-10-2017";
    public static final String DEFAULT_DEADLINE = "20-10-2017";
    public static final String DEFAULT_SINGLE_EVENT_DATE = "28-10-2018";

    private Task task;

    public TaskBuilder() {
        try {
            Description defaultDescription = new Description(DEFAULT_DESCRIPTION);
            StartDate defaultStartDate = new StartDate(DEFAULT_STARTDATE);
            Deadline defaultDeadline = new Deadline(DEFAULT_DEADLINE);
            SingleEventDate singleEventDate = new SingleEventDate(DEFAULT_SINGLE_EVENT_DATE);
            this.task = new Task(defaultDescription, defaultStartDate, defaultDeadline, singleEventDate);
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
     * Sets the {@code Description} of the {@code Task} that is being built.
     */
    public TaskBuilder withDescription(String description) {
        try {
            task.setDescription(new Description(description));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("description is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code StartDate} of the {@code Task} that is being built.
     */
    public TaskBuilder withStartDate(String startDate) {
        try {
            this.task.setStartDate(new StartDate(startDate));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Date is expected to be in the format dd-MM-yyyy.");
        }
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Task} that is being built.
     */
    public TaskBuilder withDeadline(String deadline) {
        try {
            this.task.setDeadline(new Deadline(deadline));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Date is expected to be in the format dd-MM-yyyy.");
        }
        return this;
    }

    /**
     * Sets the {@code SingleEventDate} of the {@code Task} that is being built.
     */
    public TaskBuilder withSingleEventDate(String singleEventDate) {
        try {
            this.task.setSingleEventDate(new SingleEventDate(singleEventDate));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Date is expected to be in the format dd-MM-yyyy.");
        }
        return this;
    }

    public Task build() {
        return this.task;
    }
}
