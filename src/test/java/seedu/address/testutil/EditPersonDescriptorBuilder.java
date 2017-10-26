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
        descriptor.setCompany(person.getCompany());
        descriptor.setPosition(person.getPosition());
        descriptor.setStatus(person.getStatus());
        descriptor.setPriority(person.getPriority());
        descriptor.setNote(person.getNote());
        descriptor.setPhoto(person.getPhoto());
        descriptor.setTags(person.getTags());
        descriptor.setRelation(person.getRelation());
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

    /**
     * Sets the {@code Company} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withCompany(String company) {
        try {
            ParserUtil.parseCompany(Optional.of(company)).ifPresent(descriptor::setCompany);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("company is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Position} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPosition(String position) {
        try {
            ParserUtil.parsePosition(Optional.of(position)).ifPresent(descriptor::setPosition);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("position is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withStatus(String status) {
        try {
            ParserUtil.parseStatus(Optional.of(status)).ifPresent(descriptor::setStatus);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("status is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPriority(String priority) {
        try {
            ParserUtil.parsePriority(Optional.of(priority)).ifPresent(descriptor::setPriority);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("priority is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withNote(String note) {
        try {
            ParserUtil.parseNote(Optional.of(note)).ifPresent(descriptor::setNote);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("note is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Photo} of the {@code EditPersonDescriptor} that we are
     * building.
     */
    public EditPersonDescriptorBuilder withPhoto(String note) {
        try {
            ParserUtil.parsePhoto(Optional.of(note)).ifPresent
                (descriptor::setPhoto);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Photo is expected to be "
                    + "unique.");
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

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withRelation(String... relation) {
        try {
            descriptor.setRelation(ParserUtil.parseRel(Arrays.asList(relation)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("relationships are expected to be unique.");
        }
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
