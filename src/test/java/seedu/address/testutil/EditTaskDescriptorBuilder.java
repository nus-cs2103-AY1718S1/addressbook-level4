package seedu.address.testutil;

import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.task.ReadOnlyTask;

/**
 * A utility class to help with building EditTaskDescriptor objects.
 */
public class EditTaskDescriptorBuilder {

    private EditTaskDescriptor descriptor;

    public EditTaskDescriptorBuilder() {
        descriptor = new EditTaskDescriptor();
    }

    public EditTaskDescriptorBuilder(EditTaskDescriptor descriptor) {
        this.descriptor = new EditTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTaskDescriptor} with fields containing {@code task}'s details
     */
    public EditTaskDescriptorBuilder(ReadOnlyTask task) {
        descriptor = new EditTaskDescriptor();
        descriptor.setAppointment(task.getAppointment());
        descriptor.setDate(task.getDate());
        descriptor.setStartTime(task.getStartTime());
    }

    /**
     * Sets the {@code Appointment} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withAppointment(String appointment) {
        try {
            ParserUtil.parseAppointment(Optional.of(appointment)).ifPresent(descriptor::setAppointment);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("appointment is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDate(String date) {
        try {
            ParserUtil.parseDate(Optional.of(date)).ifPresent(descriptor::setDate);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("date is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code StartTime} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withStartTime(String startTime) {
        try {
            ParserUtil.parseStartTime(Optional.of(startTime)).ifPresent(descriptor::setStartTime);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("startTime is expected to be unique.");
        }
        return this;
    }

    public EditTaskDescriptor build() {
        return descriptor;
    }
}
