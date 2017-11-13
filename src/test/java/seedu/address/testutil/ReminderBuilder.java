package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminder.DueDate;
import seedu.address.model.reminder.Priority;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderDetails;
//@@author RonakLakhotia
/**
 * A utility class to help with building Reminder objects.
 */
public class ReminderBuilder {

    public static final String DEFAULT_DETAILS = "CS2103T Assignment";
    public static final String TEST_PRIORITY = "high";
    public static final String DEFAULT_DUE_DATE = "12.11.2017";


    private Reminder reminder;

    public ReminderBuilder() {
        try {
            ReminderDetails defaultDetails = new ReminderDetails(DEFAULT_DETAILS);
            Priority defaultPriority = new Priority("Priority Level: " + TEST_PRIORITY);
            DueDate defaultDueDate = new DueDate(DEFAULT_DUE_DATE);
            this.reminder = new Reminder(defaultDetails, defaultPriority, defaultDueDate);
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
     * Sets the {@code details} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withDetails(String details) {
        try {
            this.reminder.setDetails(new ReminderDetails(details));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("details are expected to be unique.");
        }
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
     * Sets the {@code DueDate} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withDueDate(String date) {
        try {
            this.reminder.setDueDate(new DueDate(date));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("email is not expected to be unique.");
        }
        return this;
    }


    public Reminder build() {
        return this.reminder;
    }

}
