package seedu.address.testutil;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.HomeNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.SchEmail;
import seedu.address.model.person.Website;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_HOME_NUMBER = "65822291";
    public static final String DEFAULT_SCH_EMAIL = "alicepauline@u.nus.edu";
    public static final String DEFAULT_WEBSITE = "https://www.facebook.com/AlicePaul";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_BIRTHDAY = "12/11/1998";
    public static final String DEFAULT_FAVOURITE = "false";
    public static final String DEFAULT_TAGS = "friends";

    private Person person;

    public PersonBuilder() {
        try {
            Name defaultName = new Name(DEFAULT_NAME);
            Phone defaultPhone = new Phone(DEFAULT_PHONE);
            HomeNumber defaultHomeNumber = new HomeNumber(DEFAULT_HOME_NUMBER);
            Email defaultEmail = new Email(DEFAULT_EMAIL);
            SchEmail defaultSchEmail = new SchEmail(DEFAULT_SCH_EMAIL);
            Website defaultWebsite = new Website(DEFAULT_WEBSITE);
            Address defaultAddress = new Address(DEFAULT_ADDRESS);
            Birthday defaultBirthday = new Birthday(DEFAULT_BIRTHDAY);
            Boolean defaultFavourite = new Boolean(DEFAULT_FAVOURITE);
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);

            this.person = new Person(defaultName, defaultPhone, defaultHomeNumber,
                    defaultEmail, defaultSchEmail, defaultWebsite, defaultAddress,
                    defaultBirthday, defaultFavourite, defaultTags);
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
    public PersonBuilder withPhone(String phone) {
        try {
            this.person.setPhone(new Phone(phone));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phone is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code HomeNumber} of the {@code Person} that we are building.
     */
    public PersonBuilder withHomeNumber(String homeNumber) {
        try {
            this.person.setHomeNumber(new HomeNumber(homeNumber));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("home number is expected to be unique.");
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
     * Sets the {@code SchEmail} of the {@code Person} that we are building.
     */
    public PersonBuilder withSchEmail(String schEmail) {
        try {
            this.person.setSchEmail(new SchEmail(schEmail));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("school email is expected to be unique.");
        }
        return this;
    }

    //@@author DarrenCzen
    /**
     * Sets the {@code Website} of the {@code Person} that we are building.
     */
    public PersonBuilder withWebsite(String website) {
        try {
            this.person.setWebsite(new Website(website));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("website is expected to be unique.");
        }
        return this;
    }

    //@@author archthegit
    /**
     * Sets the {@code Birthday} of the {@code Person} that we are building.
     */
    public PersonBuilder withBirthday(String birthday) {
        try {
            this.person.setBirthday(new Birthday(birthday));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("birthday is expected to be unique.");
        }
        return this;
    }

    // @@author itsdickson
    /**
     * Sets the {@code Boolean} of the {@code Person} that we are building.
     */
    public PersonBuilder withFavourite(String favourite) {
        this.person.setFavourite(new Boolean(favourite));
        return this;
    }
    // @@author

    public Person build() {
        return this.person;
    }

}
