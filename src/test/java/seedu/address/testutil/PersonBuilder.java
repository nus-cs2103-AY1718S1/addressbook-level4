package seedu.address.testutil;

import static java.util.Arrays.asList;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Country;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.email.Email;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_COUNTRY_CODE = "Singapore";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, Singapore 408769";
    public static final String DEFAULT_SCHEDULE_DATE = "15-01-1997";
    public static final String DEFAULT_ACTIVITY = "Party";
    public static final String DEFAULT_TAGS = "friends";

    private Person person;

    public PersonBuilder() {
        try {
            Name defaultName = new Name(DEFAULT_NAME);
            Phone defaultPhone = new Phone(DEFAULT_PHONE);
            Country defaultCountry = new Country(DEFAULT_COUNTRY_CODE);
            Set<Email> defaultEmail = SampleDataUtil.getEmailSet(DEFAULT_EMAIL);
            Address defaultAddress = new Address(DEFAULT_ADDRESS);
            Set<Schedule> defaultSchedule = SampleDataUtil.getScheduleSet(asList(DEFAULT_SCHEDULE_DATE),
                    asList(DEFAULT_ACTIVITY), asList(DEFAULT_NAME));
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);

            this.person = new Person(defaultName, defaultPhone, defaultCountry, defaultEmail, defaultAddress,
                    defaultSchedule, defaultTags);
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
    public PersonBuilder withTags(String... tags) {
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

    //@@author icehawker
    /**
     * Sets the {@code Country} of the {@code Person} that we are building.
     */
    public PersonBuilder withCountry(String countryCode) {
        this.person.setCountry(new Country(countryCode));
        // any illegal values already caught in Phone, where code is extracted.
        return this;
    }

    //@@author 17navasaw
    /**
     * Parses the {@code emails} into a {@code Set<Email>} and sets it to the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String... emails) {
        try {
            this.person.setEmails(SampleDataUtil.getEmailSet(emails));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("emails are expected to be unique.");
        }
        return this;
    }

    //@@author
    public Person build() {
        return this.person;
    }

}
