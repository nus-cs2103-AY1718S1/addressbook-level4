package seedu.address.testutil;

import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditReminderCommand.EditReminderDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.reminders.Reminder;

//@@author justinpoh
/**
 * A utility class to help with building EditReminderDescriptor objects.
 */
public class EditReminderDescriptorBuilder {

    private EditReminderDescriptor descriptor;

    public EditReminderDescriptorBuilder() {
        descriptor = new EditReminderDescriptor();
    }

    public EditReminderDescriptorBuilder(EditReminderDescriptor descriptor) {
        this.descriptor = new EditReminderDescriptor(descriptor);
    }

    public EditReminderDescriptorBuilder(Reminder reminder) {
        descriptor = new EditReminderDescriptor();
        descriptor.setReminder(reminder.getReminder());
        descriptor.setDate(reminder.getDate());
        descriptor.setTime(reminder.getTime());
    }

    /**
     * Sets the reminder of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withReminder(String reminder) {
        if (reminder.trim().isEmpty()) {
            throw new IllegalArgumentException("reminder is expected to be unique.");
        }
        descriptor.setReminder(reminder);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withDate(String date) {
        try {
            ParserUtil.parseDate(Optional.of(date)).ifPresent(descriptor::setDate);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("date is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withTime(String time) {
        try {
            ParserUtil.parseTime(Optional.of(time)).ifPresent(descriptor::setTime);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("time is expected to be unique.");
        }
        return this;
    }

    public EditReminderDescriptor build() {
        return descriptor;
    }
}
