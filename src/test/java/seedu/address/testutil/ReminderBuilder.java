package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.property.DateTime;
import seedu.address.model.property.PropertyManager;
import seedu.address.model.property.exceptions.PropertyNotFoundException;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;


/**
 * A utility class to help with building Reminder objects.
 */
public class ReminderBuilder {


    public static final String DEFAULT_TIME = "25102010 12:00";
    public static final String DEFAULT_MESSAGE = "You have an appointment today";

    private Reminder reminder;

    static {
        PropertyManager.initializePropertyManager();
    }

    public ReminderBuilder() {
        try {
            DateTime defaultTime = new DateTime(DEFAULT_TIME);
            this.reminder = new Reminder(defaultTime, DEFAULT_MESSAGE);
        } catch (IllegalValueException | PropertyNotFoundException ive) {
            throw new AssertionError("Default reminder's values are invalid.");
        }
    }

    /**
     * Initializes the ReminderBuilder with the data of {@code ReminderToCopy}.
     */
    public ReminderBuilder(ReadOnlyReminder reminderToCopy) {
        this.reminder = new Reminder(reminderToCopy);
    }
    /**
     * Sets the {@code Date Time} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withDateTime(String time) {
        try {
            this.reminder.setDateTime(new DateTime(time));
        } catch (IllegalValueException | PropertyNotFoundException ive) {
            throw new IllegalArgumentException("Date and Time are expected to be unique.");
        }
        return this;
    }
    /**
     * Sets the {@code Message} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withMessage(String message) {
        this.reminder.setMessage(message);
        return this;
    }

    public Reminder build() {
        return this.reminder;
    }

}
