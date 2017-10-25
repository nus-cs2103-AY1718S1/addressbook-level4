package seedu.address.testutil;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindCommand.FindDetailDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class to help with building FindDetailDescriptorBuilder objects.
 */
public class FindDetailDescriptorBuilder {

    private FindDetailDescriptor descriptor;

    public FindDetailDescriptorBuilder() {
        descriptor = new FindDetailDescriptor();
    }

    /**
     * Returns an {@code FindDetailDescriptorBuilder} with fields containing {@code person}'s details
     * in string and tag set.
     */
    public FindDetailDescriptorBuilder(ReadOnlyPerson person) {
        descriptor = new FindDetailDescriptor();
        descriptor.setName(person.getName().fullName);
        descriptor.setPhone(person.getPhone().value);
        descriptor.setEmail(person.getEmail().value);
        descriptor.setAddress(person.getAddress().value);
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code name} of the {@code FindDetailDescriptor} that we are building.
     */
    public FindDetailDescriptorBuilder withName(String name) {
        Optional.of(name).ifPresent(descriptor::setName);
        return this;
    }

    /**
     * Sets the {@code phone} of the {@code FindDetailDescriptor} that we are building.
     */
    public FindDetailDescriptorBuilder withPhone(String phone) {
        Optional.of(phone).ifPresent(descriptor::setPhone);
        return this;
    }

    /**
     * Sets the {@code email} of the {@code FindDetailDescriptor} that we are building.
     */
    public FindDetailDescriptorBuilder withEmail(String email) {
        Optional.of(email).ifPresent(descriptor::setEmail);
        return this;
    }

    /**
     * Sets the {@code address} of the {@code FindDetailDescriptor} that we are building.
     */
    public FindDetailDescriptorBuilder withAddress(String address) {
        Optional.of(address).ifPresent(descriptor::setAddress);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code FindDetailDescriptorBuilder}
     * that we are building.
     */
    public FindDetailDescriptorBuilder withTags(String... tags) {
        try {
            descriptor.setTags(ParserUtil.parseTags(Arrays.asList(tags)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    public FindDetailDescriptor build() {
        return descriptor;
    }
}
