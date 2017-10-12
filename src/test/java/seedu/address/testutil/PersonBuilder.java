package seedu.address.testutil;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Parcel;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyParcel;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Parcel objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "6, Jurong West Ave 1, #08-111 S649520";
    public static final String DEFAULT_TAGS = "friends";

    private Parcel parcel;

    public PersonBuilder() {
        try {
            Name defaultName = new Name(DEFAULT_NAME);
            Phone defaultPhone = new Phone(DEFAULT_PHONE);
            Email defaultEmail = new Email(DEFAULT_EMAIL);
            Address defaultAddress = new Address(DEFAULT_ADDRESS);
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
            this.parcel = new Parcel(defaultName, defaultPhone, defaultEmail, defaultAddress, defaultTags);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default parcel's values are invalid.");
        }
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(ReadOnlyParcel personToCopy) {
        this.parcel = new Parcel(personToCopy);
    }

    /**
     * Sets the {@code Name} of the {@code Parcel} that we are building.
     */
    public PersonBuilder withName(String name) {
        try {
            this.parcel.setName(new Name(name));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Parcel} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        try {
            this.parcel.setTags(SampleDataUtil.getTagSet(tags));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Parcel} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        try {
            this.parcel.setAddress(new Address(address));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Parcel} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        try {
            this.parcel.setPhone(new Phone(phone));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phone is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Parcel} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        try {
            this.parcel.setEmail(new Email(email));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("email is expected to be unique.");
        }
        return this;
    }

    public Parcel build() {
        return this.parcel;
    }

}
