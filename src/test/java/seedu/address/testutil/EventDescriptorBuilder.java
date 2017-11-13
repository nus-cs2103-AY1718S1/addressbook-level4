package seedu.address.testutil;

import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.event.ReadOnlyEvent;

/**
 * A utility class to help with building Event objects.
 */
public class EventDescriptorBuilder {

    private EditEventDescriptor descriptor;

    public EventDescriptorBuilder() {
        descriptor = new EditEventDescriptor();
    }

    public EventDescriptorBuilder(EditEventDescriptor descriptor) {
        this.descriptor = new EditEventDescriptor(descriptor);
    }

    /**
     * Initializes the EventDescriptorBuilder with the data of {@code event}.
     */
    public EventDescriptorBuilder(ReadOnlyEvent event) {
        descriptor = new EditEventDescriptor();
        descriptor.setEventName(event.getEventName());
        descriptor.setEventDescription(event.getDescription());
        descriptor.setEventTime(event.getEventTime());
    }

    /**
     * Sets the {@code EventName} of the {@code EditEventDescriptor} that we are building.
     */
    public EventDescriptorBuilder withName(String name) {
        try {
            ParserUtil.parseEventName(Optional.of(name)).ifPresent(descriptor::setEventName);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Impossible");
        }
        return this;
    }

    /**
     * Sets the {@code EventDescription} of the {@code EditEventDescriptor} that we are building.
     */
    public EventDescriptorBuilder withDescription(String description) {
        try {
            ParserUtil.parseEventDescription(Optional.of(description)).ifPresent(descriptor::setEventDescription);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Impossible");
        }
        return this;
    }

    /**
     * Sets the {@code EventTime} of the {@code EditEventDescriptor} that we are building.
     */
    public EventDescriptorBuilder withTime(String time) {
        try {
            ParserUtil.parseEventTime((Optional.of(time))).ifPresent(descriptor::setEventTime);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Impossible");
        }
        return this;
    }

    public EditEventDescriptor build() {
        return this.descriptor;
    }

}
