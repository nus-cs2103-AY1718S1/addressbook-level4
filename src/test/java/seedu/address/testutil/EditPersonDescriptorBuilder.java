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
        descriptor.setHomeNumber(person.getHomeNumber());
        descriptor.setEmail(person.getEmail());
        descriptor.setSchEmail(person.getSchEmail());
        descriptor.setWebsite(person.getWebsite());
        descriptor.setAddress(person.getAddress());
        descriptor.setBirthday(person.getBirthday());
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
     * Sets the {@code HomeNumber} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withHomeNumber(String homeNumber) {
        try {
            ParserUtil.parseHomeNumber(Optional.of(homeNumber)).ifPresent(descriptor::setHomeNumber);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("home number is expected to be unique.");
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
     * Sets the {@code SchEmail} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withSchEmail(String schEmail) {
        try {
            ParserUtil.parseSchEmail(Optional.of(schEmail)).ifPresent(descriptor::setSchEmail);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("school email is expected to be unique.");
        }
        return this;
    }

    //@@author DarrenCzen
    /**
     * Sets the {@code Website} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withWebsite(String website) {
        try {
            ParserUtil.parseWebsite(Optional.of(website)).ifPresent(descriptor::setWebsite);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("website is expected to be unique.");
        }
        return this;
    }

    //@@author
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
    //@@author archthegit

    /**
     * Sets the {@code Birthday} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withBirthday(String birthday) {
        try {
            ParserUtil.parseBirthday(Optional.of(birthday)).ifPresent(descriptor::setBirthday);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    // @@author itsdickson
    /**
     * Sets the {@code Favourite} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withFavourite(String favourite) {
        descriptor.setFavourite(new Boolean(favourite));
        return this;
    }
    // @@author

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
