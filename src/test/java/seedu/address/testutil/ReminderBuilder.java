//@@author inGall
package seedu.address.testutil;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminder.Date;
import seedu.address.model.reminder.Message;
import seedu.address.model.reminder.Priority;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.Task;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Reminder objects.
 */
public class ReminderBuilder {

    public static final String DEFAULT_TASK = "James birthday";
    public static final String DEFAULT_PRIORITY = "Low";
    public static final String DEFAULT_DATE = "02/02/2017 20:17";
    public static final String DEFAULT_MESSAGE = "Buy present with others";
    public static final String DEFAULT_TAGS = "Watch";

    private Reminder reminder;

    public ReminderBuilder() {
        try {
            Task defaultTask = new Task(DEFAULT_TASK);
            Priority defaultPriority = new Priority(DEFAULT_PRIORITY);
            Date defaultDate = new Date(DEFAULT_DATE);
            Message defaultMessage = new Message(DEFAULT_MESSAGE);
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
            this.reminder = new Reminder(defaultTask, defaultPriority, defaultDate, defaultMessage, defaultTags);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default reminder's values are invalid.");
        }
    }

    /**
     * Initializes the ReminderBuilder with the data of {@code reminderToCopy}.
     */
    public ReminderBuilder(ReadOnlyReminder reminderToCopy) {
        this.reminder = new Reminder(reminderToCopy);
    }

    /**
     * Sets the {@code Task} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withTask(String task) {
        try {
            this.reminder.setTask(new Task(task));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("task is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Reminder} that we are building.
     */
    public ReminderBuilder withTags(String ... tags) {
        try {
            this.reminder.setTags(SampleDataUtil.getTagSet(tags));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Message} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withMessage(String message) {
        this.reminder.setMessage(new Message(message));
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withPriority(String priority) {
        try {
            this.reminder.setPriority(new Priority(priority));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("priority is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withDate(String date) {
        try {
            this.reminder.setDate(new Date(date));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("date is expected to be unique.");
        }
        return this;
    }

    public Reminder build() {
        return this.reminder;
    }

}
