package seedu.address.testutil;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditParcelDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.parcel.ReadOnlyParcel;

/**
 * A utility class to help with building EditParcelDescriptor objects.
 */
public class EditParcelDescriptorBuilder {

    private EditParcelDescriptor descriptor;

    public EditParcelDescriptorBuilder() {
        descriptor = new EditParcelDescriptor();
    }

    public EditParcelDescriptorBuilder(EditCommand.EditParcelDescriptor descriptor) {
        this.descriptor = new EditParcelDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditParcelDescriptor} with fields containing {@code parcel}'s details
     */
    public EditParcelDescriptorBuilder(ReadOnlyParcel parcel) {
        descriptor = new EditParcelDescriptor();
        descriptor.setArticleNumber(parcel.getArticleNumber());
        descriptor.setName(parcel.getName());
        descriptor.setPhone(parcel.getPhone());
        descriptor.setEmail(parcel.getEmail());
        descriptor.setAddress(parcel.getAddress());
        descriptor.setTags(parcel.getTags());
    }

    /**
     * Sets the {@code ArticleNumber} of the {@code EditParcelDescriptor} that we are building.
     */
    public EditParcelDescriptorBuilder withArticleNumber(String articleNumber) {
        try {
            ParserUtil.parseArticleNumber(Optional.of(articleNumber)).ifPresent(descriptor::setArticleNumber);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("article number is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code EditParcelDescriptor} that we are building.
     */
    public EditParcelDescriptorBuilder withName(String name) {
        try {
            ParserUtil.parseName(Optional.of(name)).ifPresent(descriptor::setName);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditParcelDescriptor} that we are building.
     */
    public EditParcelDescriptorBuilder withPhone(String phone) {
        try {
            ParserUtil.parsePhone(Optional.of(phone)).ifPresent(descriptor::setPhone);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phone is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditParcelDescriptor} that we are building.
     */
    public EditParcelDescriptorBuilder withEmail(String email) {
        try {
            ParserUtil.parseEmail(Optional.of(email)).ifPresent(descriptor::setEmail);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("email is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditParcelDescriptor} that we are building.
     */
    public EditParcelDescriptorBuilder withAddress(String address) {
        try {
            ParserUtil.parseAddress(Optional.of(address)).ifPresent(descriptor::setAddress);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditParcelDescriptor}
     * that we are building.
     */
    public EditParcelDescriptorBuilder withTags(String... tags) {
        try {
            descriptor.setTags(ParserUtil.parseTags(Arrays.asList(tags)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    public EditCommand.EditParcelDescriptor build() {
        return descriptor;
    }
}
