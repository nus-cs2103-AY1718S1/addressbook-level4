package seedu.address.testutil;

import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.event.EditEventCommand.EditEventDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.property.exceptions.PropertyNotFoundException;

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
        descriptor.setName(event.getName());
        descriptor.setTime(event.getTime());
        descriptor.setVenue(event.getVenue());
    }

    /**
     * Sets the {@code Name} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withName(String name) {
        try {
            ParserUtil.parseName(Optional.of(name)).ifPresent(descriptor::setName);
        } catch (IllegalValueException | PropertyNotFoundException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withTime(String dateTime) {
        try {
            ParserUtil.parseTime(Optional.of(dateTime)).ifPresent(descriptor::setTime);
        } catch (IllegalValueException | PropertyNotFoundException ive) {
            throw new IllegalArgumentException("date/time is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Venue} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withVenue(String venue) {
        try {
            ParserUtil.parseAddress(Optional.of(venue)).ifPresent(descriptor::setVenue);
        } catch (IllegalValueException | PropertyNotFoundException ive) {
            throw new IllegalArgumentException("Venue is expected to be unique.");
        }
        return this;
    }

    public EditEventDescriptor build() {
        return descriptor;
    }
}
