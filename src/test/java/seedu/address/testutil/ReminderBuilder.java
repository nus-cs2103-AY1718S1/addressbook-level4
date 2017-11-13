package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminders.Date;
import seedu.address.model.reminders.ReadOnlyReminder;
import seedu.address.model.reminders.Reminder;
import seedu.address.model.reminders.Time;

//@@author justinpoh
/**
 * A utility class to help with building Reminder objects.
 */
public class ReminderBuilder {

    public static final String DEFAULT_REMINDER = "Drink coffee";
    public static final String DEFAULT_DATE = "01/11/2017";
    public static final String DEFAULT_TIME = "08:00";

    private Reminder reminder;

    public ReminderBuilder() {
        try {
            Date date = new Date(DEFAULT_DATE);
            Time time = new Time(DEFAULT_TIME);
            this.reminder = new Reminder(DEFAULT_REMINDER, date, time);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default values cannot be wrong!");
        }
    }

    /**
     * Initializes the ReminderBuilder with the data of {@code reminderToCopy}.
     */
    public ReminderBuilder(ReadOnlyReminder reminderToCopy) {
        this.reminder = new Reminder(reminderToCopy);
    }

    /**
     * Sets the {@code reminder} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withReminder(String reminder) {
        this.reminder.setReminder(reminder);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withTime(String time) {
        try {
            this.reminder.setTime(new Time(time));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
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
