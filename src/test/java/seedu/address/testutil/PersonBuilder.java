package seedu.address.testutil;

import java.io.IOException;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.NokName;
import seedu.address.model.person.NokPhone;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_AVATAR = "default.png";
    public static final String DEFAULT_NOK_NAME = "Beth Pauline";
    public static final String DEFAULT_NOK_PHONE = "84541946";
    public static final String DEFAULT_TAGS = "friends";

    private Person person;

    public PersonBuilder() {
        try {
            Name defaultName = new Name(DEFAULT_NAME);
            Phone defaultPhone = new Phone(DEFAULT_PHONE);
            Email defaultEmail = new Email(DEFAULT_EMAIL);
            Address defaultAddress = new Address(DEFAULT_ADDRESS);
            Avatar defaultAvatar = new Avatar(DEFAULT_AVATAR);
            NokName defaultNokName = new NokName(DEFAULT_NOK_NAME);
            NokPhone defaultNokPhone = new NokPhone(DEFAULT_NOK_PHONE);
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
            this.person = new Person(defaultName, defaultPhone, defaultEmail, defaultAddress,
                    defaultAvatar, defaultNokName, defaultNokPhone, defaultTags);
        } catch (IllegalValueException | IOException ive) {
            throw new AssertionError("Default person's values are invalid.");
        }
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(ReadOnlyPerson personToCopy) {
        this.person = new Person(personToCopy);
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        try {
            this.person.setName(new Name(name));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        try {
            this.person.setPhone(new Phone(phone));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phone is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        try {
            this.person.setEmail(new Email(email));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("email is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        try {
            this.person.setAddress(new Address(address));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code NokName} of the {@code Person} that we are building.
     */
    public PersonBuilder withNokName(String nokName) {
        try {
            this.person.setNokName(new NokName(nokName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("NOK name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code NokPhone} of the {@code Person} that we are building.
     */
    public PersonBuilder withNokPhone(String nokPhone) {
        try {
            this.person.setNokPhone(new NokPhone(nokPhone));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("NOK phone is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        try {
            this.person.setTags(SampleDataUtil.getTagSet(tags));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    //@@author yuheng222
    /**
     * Sets the {@code Avatar} of the {@code Person} that we are building.
     */
    public PersonBuilder withAvatar(String path) {
        try {
            this.person.setAvatar(new Avatar(path));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("avatar is expected to be unique.");
        } catch (IOException e) {
            throw new IllegalArgumentException("filepath is invalid.");
        }
        return this;
    }
    //@@author

    public Person build() {
        return this.person;
    }

}
