package seedu.address.testutil;

import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.event.ReadOnlyEvent;

//@@author royceljh
/**
 * A utility class to help with building EditEventDescriptor objects.
 */
public class EditEventDescriptorBuilder {

    private EditEventDescriptor descriptor;

    public EditEventDescriptorBuilder() {
        descriptor = new EditEventDescriptor();
    }

    public EditEventDescriptorBuilder(EditEventDescriptor descriptor) {
        this.descriptor = new EditEventDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEventDescriptor} with fields containing {@code event}'s details
     */
    public EditEventDescriptorBuilder(ReadOnlyEvent event) {
        descriptor = new EditEventDescriptor();
        descriptor.setHeader(event.getHeader());
        descriptor.setDesc(event.getDesc());
        descriptor.setEventDate(event.getEventDate());
    }

    /**
     * Sets the {@code Header} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withHeader(String header) {
        try {
            ParserUtil.parseHeader(Optional.of(header)).ifPresent(descriptor::setHeader);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("header is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Desc} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withDesc(String desc) {
        try {
            ParserUtil.parseDesc(Optional.of(desc)).ifPresent(descriptor::setDesc);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("desc is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code EventDate} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEventDate(String eventDate) {
        try {
            ParserUtil.parseEventDate(Optional.of(eventDate)).ifPresent(descriptor::setEventDate);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("eventDate is expected to be unique.");
        }
        return this;
    }

    public EditEventDescriptor build() {
        return descriptor;
    }
}
