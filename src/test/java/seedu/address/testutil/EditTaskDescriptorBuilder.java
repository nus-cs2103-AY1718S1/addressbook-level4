//@@author srishag
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
     * Returns an {@code EditTaskDescriptor} with fields containing {@code Task}'s details
     */
    public EditTaskDescriptorBuilder(ReadOnlyTask task) {
        descriptor = new EditTaskDescriptor();
        descriptor.setHeader(task.getHeader());
        descriptor.setDesc(task.getDesc());
        descriptor.setDeadline(task.getDeadline());
    }

    /**
     * Sets the {@code header} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withHeader(String header) {
        try {
            ParserUtil.parseHeader(Optional.of(header)).ifPresent(descriptor::setHeader);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("header is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code desc} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDesc(String desc) {
        try {
            ParserUtil.parseDesc(Optional.of(desc)).ifPresent(descriptor::setDesc);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("desc is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code deadline} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDeadline(String deadline) {
        try {
            ParserUtil.parseDeadline(Optional.of(deadline)).ifPresent(descriptor::setDeadline);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("deadline is expected to be unique.");
        }
        return this;
    }


    public EditTaskDescriptor build() {
        return descriptor;
    }
}
