//@@author inGall
package seedu.address.testutil;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditReminderCommand.EditReminderDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.reminder.ReadOnlyReminder;

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

    /**
     * Returns an {@code EditReminderDescriptor} with fields containing {@code reminder}'s details
     */
    public EditReminderDescriptorBuilder(ReadOnlyReminder reminder) {
        descriptor = new EditReminderDescriptor();
        descriptor.setTask(reminder.getTask());
        descriptor.setPriority(reminder.getPriority());
        descriptor.setDate(reminder.getDate());
        descriptor.setMessage(reminder.getMessage());
        descriptor.setTags(reminder.getTags());
    }

    /**
     * Sets the {@code Task} of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withTask(String task) {
        try {
            ParserUtil.parseTask(Optional.of(task)).ifPresent(descriptor::setTask);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("task is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withPriority(String priority) {
        try {
            ParserUtil.parsePriority(Optional.of(priority)).ifPresent(descriptor::setPriority);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("priority is expected to be unique.");
        }
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
     * Sets the {@code Message} of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withMessage(String message) {
        try {
            ParserUtil.parseMessage(Optional.of(message)).ifPresent(descriptor::setMessage);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("message is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditReminderDescriptor}
     * that we are building.
     */
    public EditReminderDescriptorBuilder withTags(String... tags) {
        try {
            descriptor.setTags(ParserUtil.parseTags(Arrays.asList(tags)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    public EditReminderDescriptor build() {
        return descriptor;
    }
}
