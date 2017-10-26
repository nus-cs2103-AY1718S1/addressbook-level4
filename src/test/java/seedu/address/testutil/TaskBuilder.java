package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.SUFFIX_NO_RECUR_INTERVAL;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.Suffix;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.StartDate;
import seedu.address.model.task.Task;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_DESCRIPTION = "CS2103T assignment due";
    public static final String DEFAULT_STARTDATE = "Tue, Oct 24, '17";
    public static final String DEFAULT_DEADLINE = "Thu, Oct 26, '17";
    public static final Suffix DEFAULT_RECUR_INTERVAL = SUFFIX_NO_RECUR_INTERVAL;
    public static final String DEFAULT_TAG = "urgent";

    private Task task;

    public TaskBuilder() {
        try {
            Description defaultDescription = new Description(DEFAULT_DESCRIPTION);
            StartDate defaultStartDate = new StartDate(DEFAULT_STARTDATE, DEFAULT_RECUR_INTERVAL);
            Deadline defaultDeadline = new Deadline(DEFAULT_DEADLINE, DEFAULT_RECUR_INTERVAL);
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAG);
            this.task = new Task(defaultDescription, defaultStartDate, defaultDeadline, defaultTags);
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
            this.task.setStartDate(new StartDate(startDate, DEFAULT_RECUR_INTERVAL));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("dates should be unique and in the correct format");
        }
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Task} that is being built.
     */
    public TaskBuilder withDeadline(String deadline) {
        try {
            this.task.setDeadline(new Deadline(deadline, DEFAULT_RECUR_INTERVAL));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("dates should be unique and in the correct format");
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Task} that we are building.
     */
    public TaskBuilder withTags(String ... tags) {
        try {
            this.task.setTags(SampleDataUtil.getTagSet(tags));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    public Task build() {
        return this.task;
    }
}
