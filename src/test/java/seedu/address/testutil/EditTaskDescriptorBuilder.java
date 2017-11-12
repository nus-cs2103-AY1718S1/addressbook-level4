//@@author KongjiaQi
package seedu.address.testutil;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Description;
import seedu.address.model.task.Name;
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
        descriptor.setName(task.getName());
        descriptor.setDescription(task.getDescription());
        descriptor.setStart(task.getStartDateTime());
        descriptor.setEnd(task.getEndDateTime());
        descriptor.setTags(task.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withName(String name) {
        try {
            Optional<String> parserName = ParserUtil.parseString(Optional.of(name));
            if (parserName.isPresent()) {
                descriptor.setName(new Name(name));
            }
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDescription(String description) {
        try {
            Optional<String> parserDescription = ParserUtil.parseString(Optional.of(description));
            if (parserDescription.isPresent()) {
                descriptor.setDescription(new Description(description));
            }
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("description is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Start} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withStart(String start) {
        try {
            Optional<String> parserStartTime = ParserUtil.parseString(Optional.of(start));
            if (parserStartTime.isPresent()) {
                descriptor.setStart(new DateTime(start));
            }
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException(ive.getMessage());
        }
        return this;
    }

    /**
     * Sets the {@code End} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withEnd(String end) {
        try {
            Optional<String> parserEndTime = ParserUtil.parseString(Optional.of(end));
            if (parserEndTime.isPresent()) {
                descriptor.setEnd(new DateTime(end));
            }
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException(ive.getMessage());
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditTaskDescriptor}
     * that we are building.
     */
    public EditTaskDescriptorBuilder withTags(String... tags) {
        try {
            descriptor.setTags(ParserUtil.parseTags(Arrays.asList(tags)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    public EditTaskDescriptor build() {
        return descriptor;
    }
}
