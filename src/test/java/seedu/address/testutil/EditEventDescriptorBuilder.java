//@@author A0162268B
package seedu.address.testutil;

import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.event.EditEventCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.event.ReadOnlyEvent;

/**
 * A utility class to help with building EditEventDescriptor objects.
 */
public class EditEventDescriptorBuilder {

    private EditEventCommand.EditEventDescriptor descriptor;

    public EditEventDescriptorBuilder() {
        descriptor = new EditEventCommand.EditEventDescriptor();
    }

    public EditEventDescriptorBuilder(EditEventCommand.EditEventDescriptor descriptor) {
        this.descriptor = new EditEventCommand.EditEventDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEventDescriptor} with fields containing {@code event}'s details
     */
    public EditEventDescriptorBuilder(ReadOnlyEvent event) {
        descriptor = new EditEventCommand.EditEventDescriptor();
        descriptor.setTitle(event.getTitle());
        descriptor.setTimeslot(event.getTimeslot());
        descriptor.setDescription(event.getDescription());
    }

    /**
     * Sets the {@code Title} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withTitle(String title) {
        try {
            ParserUtil.parseTitle(Optional.of(title)).ifPresent(descriptor::setTitle);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("title is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Timeslot} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withTimeslot(String timeslot) {
        try {
            ParserUtil.parseTimeslot(Optional.of(timeslot)).ifPresent(descriptor::setTimeslot);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Timeslot is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withDescription(String description) {
        try {
            ParserUtil.parseDescription(Optional.of(description)).ifPresent(descriptor::setDescription);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Description is expected to be unique.");
        }
        return this;
    }

    public EditEventCommand.EditEventDescriptor build() {
        return descriptor;
    }
}
