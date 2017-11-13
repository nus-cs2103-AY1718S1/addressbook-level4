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
        descriptor.setParentPhone(person.getParentPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setFormClass(person.getFormClass());
        descriptor.setGrades(person.getGrades());
        descriptor.setPostalCode(person.getPostalCode());
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

    //@@author Lenaldnwj
    /**
     * Sets the {@code ParentPhone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withParentPhone(String parentPhone) {
        try {
            ParserUtil.parseParentPhone(Optional.of(parentPhone)).ifPresent(descriptor::setParentPhone);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("parentPhone is expected to be unique.");
        }
        return this;
    }
    //@@author

    //@@author lincredibleJC
    /**
     * Sets the {@code FormClass} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withFormClass(String formClass) {
        try {
            ParserUtil.parseFormClass(Optional.of(formClass)).ifPresent(descriptor::setFormClass);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("formClass is expected to be unique.");
        }
        return this;
    }
    //@@author

    //@@author lincredibleJC
    /**
     * Sets the {@code Grades} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withGrades(String grades) {
        try {
            ParserUtil.parseGrades(Optional.of(grades)).ifPresent(descriptor::setGrades);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("grades is expected to be unique.");
        }
        return this;
    }
    //@@author

    /**
     * Sets the {@code PostalCode} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPostalCode(String postalCode) {
        try {
            ParserUtil.parsePostalCode(Optional.of(postalCode)).ifPresent(descriptor::setPostalCode);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("postal code is expected to be unique.");
        }
        return this;
    }

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

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
