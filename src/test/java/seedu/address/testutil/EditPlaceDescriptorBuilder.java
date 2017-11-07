package seedu.address.testutil;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPlaceDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.place.ReadOnlyPlace;

/**
 * A utility class to help with building EditPlaceDescriptor objects.
 */
public class EditPlaceDescriptorBuilder {

    private EditPlaceDescriptor descriptor;

    public EditPlaceDescriptorBuilder() {
        descriptor = new EditCommand.EditPlaceDescriptor();
    }

    public EditPlaceDescriptorBuilder(EditCommand.EditPlaceDescriptor descriptor) {
        this.descriptor = new EditCommand.EditPlaceDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPlaceDescriptor} with fields containing {@code place}'s details
     */
    public EditPlaceDescriptorBuilder(ReadOnlyPlace place) {
        descriptor = new EditCommand.EditPlaceDescriptor();
        descriptor.setName(place.getName());
        descriptor.setPhone(place.getPhone());
        descriptor.setWebsite(place.getWebsite());
        descriptor.setAddress(place.getAddress());
        descriptor.setPostalCode(place.getPostalCode());
        descriptor.setTags(place.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPlaceDescriptor} that we are building.
     */
    public EditPlaceDescriptorBuilder withName(String name) {
        try {
            ParserUtil.parseName(Optional.of(name)).ifPresent(descriptor::setName);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPlaceDescriptor} that we are building.
     */
    public EditPlaceDescriptorBuilder withPhone(String phone) {
        try {
            ParserUtil.parsePhone(Optional.of(phone)).ifPresent(descriptor::setPhone);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phone is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPlaceDescriptor} that we are building.
     */
    public EditPlaceDescriptorBuilder withWebsite(String website) {
        try {
            ParserUtil.parseWebsite(Optional.of(website)).ifPresent(descriptor::setWebsite);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("website is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPlaceDescriptor} that we are building.
     */
    public EditPlaceDescriptorBuilder withAddress(String address) {
        try {
            ParserUtil.parseAddress(Optional.of(address)).ifPresent(descriptor::setAddress);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPlaceDescriptor} that we are building.
     */
    public EditPlaceDescriptorBuilder withPostalCode(String postalcode) {
        try {
            ParserUtil.parsePostalCode(Optional.of(postalcode)).ifPresent(descriptor::setPostalCode);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phone is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPlaceDescriptor}
     * that we are building.
     */
    public EditPlaceDescriptorBuilder withTags(String... tags) {
        try {
            descriptor.setTags(ParserUtil.parseTags(Arrays.asList(tags)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    public EditCommand.EditPlaceDescriptor build() {
        return descriptor;
    }
}
