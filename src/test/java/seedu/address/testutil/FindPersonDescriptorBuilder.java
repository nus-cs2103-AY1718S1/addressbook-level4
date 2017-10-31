//@@author chrisboo
package seedu.address.testutil;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindCommand.FindPersonDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class to help with building FindPersonDescriptor objects.
 */
public class FindPersonDescriptorBuilder {

    private FindPersonDescriptor descriptor;

    public FindPersonDescriptorBuilder() {
        descriptor = new FindPersonDescriptor();
    }

    public FindPersonDescriptorBuilder(FindPersonDescriptor descriptor) {
        this.descriptor = new FindPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code FindPersonDescriptor} with fields containing {@code person}'s details
     */
    public FindPersonDescriptorBuilder(ReadOnlyPerson person) {
        descriptor = new FindPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setBirthday(person.getBirthday());
        descriptor.setWebsite(person.getWebsite());
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code FindPersonDescriptor} that we are building.
     */
    public FindPersonDescriptorBuilder withName(String name) {
        try {
            ParserUtil.parseName(Optional.of(name)).ifPresent(descriptor::setName);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code FindPersonDescriptor} that we are building.
     */
    public FindPersonDescriptorBuilder withPhone(String phone) {
        try {
            ParserUtil.parsePhone(Optional.of(phone)).ifPresent(descriptor::setPhone);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phone is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code FindPersonDescriptor} that we are building.
     */
    public FindPersonDescriptorBuilder withEmail(String email) {
        try {
            ParserUtil.parseEmail(Optional.of(email)).ifPresent(descriptor::setEmail);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("email is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code FindPersonDescriptor} that we are building.
     */
    public FindPersonDescriptorBuilder withAddress(String address) {
        try {
            ParserUtil.parseAddress(Optional.of(address)).ifPresent(descriptor::setAddress);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Birthday} of the {@code FindPersonDescriptor} that we are building.
     */
    public FindPersonDescriptorBuilder withBirthday(String birthday) {
        try {
            ParserUtil.parseBirthday(Optional.of(birthday)).ifPresent(descriptor::setBirthday);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Birthday should be id dd/MM/yyyy format");
        }
        return this;
    }

    /**
     * Sets the {@code Website} of the {@code FindPersonDescriptor} that we are building.
     */
    public FindPersonDescriptorBuilder withWebsite(String website) {
        try {
            ParserUtil.parseWebsite(Optional.of(website)).ifPresent(descriptor::setWebsite);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Website is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code FindPersonDescriptor}
     * that we are building.
     */
    public FindPersonDescriptorBuilder withTags(String... tags) {
        try {
            descriptor.setTags(ParserUtil.parseTags(Arrays.asList(tags)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    public FindPersonDescriptor build() {
        return descriptor;
    }
}
