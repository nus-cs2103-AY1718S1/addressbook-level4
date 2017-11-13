package seedu.address.testutil;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.EventTime;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.util.SampleDataUtil;

//@@author raisa2010
/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_DESCRIPTION = "CS2103T assignment due";
    public static final String DEFAULT_DEADLINE = "Thu, Oct 26, '17";
    public static final String DEFAULT_START_TIME = "10:00";
    public static final String DEFAULT_END_TIME = "22:00";
    public static final String DEFAULT_TAG = "urgent";

    private Task task;

    public TaskBuilder() {
        try {
            Description defaultDescription = new Description(DEFAULT_DESCRIPTION);
            Deadline defaultDeadline = new Deadline(DEFAULT_DEADLINE);
            EventTime defaultStartTime = new EventTime(DEFAULT_START_TIME);
            EventTime defaultEndTime = new EventTime(DEFAULT_END_TIME);
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAG);
            this.task = new Task(defaultDescription, defaultDeadline, defaultStartTime, defaultEndTime, defaultTags);
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
     * Sets the {@code Deadline} of the {@code Task} that is being built.
     */
    public TaskBuilder withDeadline(String deadline) {
        this.task.setDeadline(new Deadline(deadline));
        return this;
    }

    /**
     * Sets the {@code startTime} of the {@code Task} that is begin built.
     */
    public TaskBuilder withStartTime(String startTime) {
        this.task.setStartTime(new EventTime(startTime));
        return this;
    }

    /**
     * Sets the {@code endTime} of the {@code Task} that is begin built.
     */
    public TaskBuilder withEndTime(String endTime) {
        this.task.setEndTime(new EventTime(endTime));
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
