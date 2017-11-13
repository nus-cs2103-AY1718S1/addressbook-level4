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
        descriptor.setDescription(task.getDescription());
        descriptor.setPriority(task.getPriority());
        descriptor.setDueDate(task.getDueDate());
    }

    /**
     * Sets the {@code Task} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDescription(String description) {
        try {
            //ParserUtil.parseDescription(description);
            ParserUtil.parseDescriptionOptional(Optional.of(description)).ifPresent(descriptor::setDescription);
        } catch (IllegalValueException ive) {
            //throw new IllegalArgumentException("description is expected to be unique.");
            // it is ok for description to be repetitive
        }
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withPriority(String priority) {
        try {
            ParserUtil.parsePriority(Optional.of(priority)).ifPresent(descriptor::setPriority);
        } catch (IllegalValueException ive) {
            //throw new IllegalArgumentException("priority is expected to be unique.");
            // it is ok for priority to be repetitive
        }
        return this;
    }

    /**
     * Sets the {@code DueDate} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDueDate(String dueDate) {
        try {
            ParserUtil.parseDueDate(Optional.of(dueDate)).ifPresent(descriptor::setDueDate);
        } catch (IllegalValueException ive) {
            //throw new IllegalArgumentException("due date is expected to be unique.");
            // it is ok for duedate to be repetitive
        }
        return this;
    }

    public EditTaskDescriptor build() {
        return descriptor;
    }
}
