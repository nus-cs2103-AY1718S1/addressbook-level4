package seedu.address.testutil;

import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangeReminderCommand.ChangeReminderDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.reminder.ReadOnlyReminder;

//@@author RonakLakhotia
/**
 * A utility class to help with building ChangeReminderDescriptor objects.
 */
public class ChangeReminderDescriptorBuilder {

    private ChangeReminderDescriptor descriptor;

    public ChangeReminderDescriptorBuilder() {
        descriptor = new ChangeReminderDescriptor();
    }

    public ChangeReminderDescriptorBuilder(ChangeReminderDescriptor descriptor) {
        this.descriptor = new ChangeReminderDescriptor(descriptor);
    }

    /**
     * Returns an {@code ChangeReminderDescriptor} with fields containing {@code person}'s details
     */
    public ChangeReminderDescriptorBuilder(ReadOnlyReminder reminder) {
        descriptor = new ChangeReminderDescriptor();
        descriptor.setDetails(reminder.getDetails());
        descriptor.setPriority(reminder.getPriority());
        descriptor.setDueDate(reminder.getDueDate());
    }

    /**
     * Sets the {@code details} of the {@code ChangeReminderDescriptor} that we are building.
     */
    public ChangeReminderDescriptorBuilder withDetails(String details) {
        try {
            ParserUtil.parseDetails(Optional.of(details)).ifPresent(descriptor::setDetails);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("details are expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code ChangeReminderDescriptor} that we are building.
     */
    public ChangeReminderDescriptorBuilder withPriority(String priority) {
        try {
            ParserUtil.parsePriority(Optional.of(priority)).ifPresent(descriptor::setPriority);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("priority is not expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code DueDate} of the {@code ChangeReminderDescriptor} that we are building.
     */
    public ChangeReminderDescriptorBuilder withDueDate(String date) {
        try {
            ParserUtil.parseDueDate(Optional.of(date)).ifPresent(descriptor::setDueDate);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("due date is not expected to be unique.");
        }
        return this;
    }



    public ChangeReminderDescriptor build() {
        return descriptor;
    }
}
