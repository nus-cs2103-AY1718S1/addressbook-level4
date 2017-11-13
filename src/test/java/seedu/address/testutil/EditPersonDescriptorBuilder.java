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
        descriptor.setHandphone(person.getHandphone());
        descriptor.setHomePhone(person.getHomePhone());
        descriptor.setOfficePhone(person.getOfficePhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setPostalCode(person.getPostalCode());
        descriptor.setDebt(person.getDebt());
        descriptor.setTotalDebt(person.getTotalDebt());
        descriptor.setInterest(person.getInterest());
        descriptor.setDeadline(person.getDeadline());
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
     * Sets the {@code Handphone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withHandphone(String handphone) {
        try {
            ParserUtil.parseHandphone(Optional.of(handphone)).ifPresent(descriptor::setHandphone);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("handphone is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code HomePhone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withHomePhone(String homePhone) {
        try {
            ParserUtil.parseHomePhone(Optional.of(homePhone)).ifPresent(descriptor::setHomePhone);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("home phone is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code OfficePhone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withOfficePhone(String officePhone) {
        try {
            ParserUtil.parseOfficePhone(Optional.of(officePhone)).ifPresent(descriptor::setOfficePhone);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("office phone is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Debt} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withDebt(String debt) {
        try {
            ParserUtil.parseDebt(Optional.of(debt)).ifPresent(descriptor::setDebt);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("debt is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code totalDebt} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withTotalDebt(String totalDebt) {
        try {
            ParserUtil.parseDebt(Optional.of(totalDebt)).ifPresent(descriptor::setTotalDebt);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("total debt is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Interest} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withInterest(String interest) {
        try {
            ParserUtil.parseInterest(Optional.of(interest)).ifPresent(descriptor::setInterest);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("interest is expected to be unique.");
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
     * Sets the {@code Deadline} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withDeadline(String deadline) {
        try {
            ParserUtil.parseDeadlineForEdit(Optional.of(deadline)).ifPresent(descriptor::setDeadline);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("deadline is expected to be unique.");
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
