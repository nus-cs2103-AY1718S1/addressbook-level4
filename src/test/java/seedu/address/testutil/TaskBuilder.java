package seedu.address.testutil;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_NAME = "picnic";
    public static final String DEFAULT_DESCRIPTION = "have fun at Botanic Garden";
    public static final String DEFAULT_START_DATE_TIME = "26/11/2017 12:00pm";
    public static final String DEFAULT_END_DATE_TIME = "26/11/2017 15:00pm";
    public static final int DEFAULT_PRIORITY = 3;
    public static final String DEFAULT_TAGS = "friends";

    private Task task;

    public TaskBuilder() {
        try {
            String defaultName = new String(DEFAULT_NAME);
            String defaultDescription = new String(DEFAULT_DESCRIPTION);
            String defaultStart = new String(DEFAULT_START_DATE_TIME);
            String defaultEnd = new String(DEFAULT_END_DATE_TIME);
            Integer defaultPriority = new Integer(DEFAULT_PRIORITY);
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
            this.task = new Task(defaultName, defaultDescription, defaultStart, defaultEnd,
                    defaultTags, false, defaultPriority);
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
     * Sets the {@code Name} of the {@code Task} that we are building.
     */
    public TaskBuilder withName(String name) {
        this.task.setName(name);
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

    /**
     * Sets the {@code Description} of the {@code Task} that we are building.
     */
    public TaskBuilder withDescription(String description) {
        this.task.setDescription(description);
        return this;
    }

    /**
     * Sets the {@code Start} of the {@code Task} that we are building.
     */
    public TaskBuilder withStart(String start) {
        this.task.setStartDateTime(start);
        return this;
    }

    /**
     * Sets the {@code End} of the {@code Task} that we are building.
     */
    public TaskBuilder withEnd(String end) {
        this.task.setEndDateTime(end);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */

    public Task build() {
        return this.task;
    }

}
