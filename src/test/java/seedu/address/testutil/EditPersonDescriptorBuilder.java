package seedu.address.testutil;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(ReadOnlyPerson person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        //@@author Pujitha97
        descriptor.setDateOfBirth(person.getDateOfBirth());
        descriptor.setGender(person.getGender());
        //@@author
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        try {
            ParserUtil.parseName(Optional.of(name)).ifPresent(descriptor::setName);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        try {
            ParserUtil.parsePhone(Optional.of(phone)).ifPresent(descriptor::setPhone);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phone is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        try {
            ParserUtil.parseEmail(Optional.of(email)).ifPresent(descriptor::setEmail);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("email is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        try {
            ParserUtil.parseAddress(Optional.of(address)).ifPresent(descriptor::setAddress);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }
    //@@author Pujitha97
    /**
     * Sets the {@code Date of Birth} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withDateOfBirth(String dob) {
        try {
            ParserUtil.parseDateOfBirth(Optional.of(dob)).ifPresent(descriptor::setDateOfBirth);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Date of birth is expected to be unique.");
        }
        return this;
    }
    /**
     * Sets the {@code Gender} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withGender(String gender) {
        try {
            ParserUtil.parseGender(Optional.of(gender)).ifPresent(descriptor::setGender);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Gender is expected to be unique.");
        }
        return this;
    }
    //@@author
    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        try {
            descriptor.setTags(ParserUtil.parseTags(Arrays.asList(tags)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    /**
     * Parses the {@code tagsToDel} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withDelTags(String... tagsToDel) {
        try {
            descriptor.setTagsToDel(ParserUtil.parseTags(Arrays.asList(tagsToDel)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
