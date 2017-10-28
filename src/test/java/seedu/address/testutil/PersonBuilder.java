package seedu.address.testutil;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.mod.Mod;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Photo;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONES = "85355255";
    public static final String DEFAULT_BIRTHDAY = "25/09/1990";
    public static final String DEFAULT_EMAILS = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_MODS = "friends";

    private Person person;

    public PersonBuilder() {
        try {
            Name defaultName = new Name(DEFAULT_NAME);
            Set<Phone> defaultPhones = SampleDataUtil.getPhoneSet(DEFAULT_PHONES);
            Birthday defaultBirthday = new Birthday(DEFAULT_BIRTHDAY);
            Set<Email> defaultEmails = SampleDataUtil.getEmailSet(DEFAULT_EMAILS);
            Address defaultAddress = new Address(DEFAULT_ADDRESS);
            Photo defaultPhoto = new Photo();
            Set<Mod> defaultTags = SampleDataUtil.getModSet(DEFAULT_MODS);
            this.person = new Person(defaultName, defaultPhones, defaultBirthday, defaultEmails,
                    defaultAddress, defaultPhoto, defaultTags);
        } catch (IllegalValueException ive) {
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
     * Parses the {@code mods} into a {@code Set<Mod>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... mods) {
        try {
            this.person.setTags(SampleDataUtil.getModSet(mods));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("mods are expected to be unique.");
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
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhones(String ... phones) {
        try {
            this.person.setPhones(SampleDataUtil.getPhoneSet(phones));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phone is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Birthday} of the {@code Birthday} that we are building.
     */
    public PersonBuilder withBirthday(String birthday) {
        try {
            this.person.setBirthday(new Birthday(birthday));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("birthday is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmails(String ... emails) {
        try {
            this.person.setEmails(SampleDataUtil.getEmailSet(emails));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("email is expected to be unique.");
        }
        return this;
    }

    public Person build() {
        return this.person;
    }

}
