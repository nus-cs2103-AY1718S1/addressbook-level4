package seedu.address.testutil;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Cluster;
import seedu.address.model.person.DateBorrow;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Debt;
import seedu.address.model.person.Email;
import seedu.address.model.person.Handphone;
import seedu.address.model.person.HomePhone;
import seedu.address.model.person.Interest;
import seedu.address.model.person.Name;
import seedu.address.model.person.OfficePhone;
import seedu.address.model.person.Person;
import seedu.address.model.person.PostalCode;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_HANDPHONE = "85355255";
    public static final String DEFAULT_HOME_PHONE = "65355255";
    public static final String DEFAULT_OFFICE_PHONE = "60005255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_POSTAL_CODE = "600123";
    public static final String DEFAULT_DEBT = "123456789";
    public static final String DEFAULT_INTEREST = "1";
    // To avoid the scenario where a test case instantiates a Person with DateBorrow that is later
    // than the Deadline.
    public static final String DEFAULT_DEADLINE = Deadline.NO_DEADLINE_SET;
    public static final String DEFAULT_TAGS = "friends";

    private Person person;

    public PersonBuilder() {
        try {
            Name defaultName = new Name(DEFAULT_NAME);
            Handphone defaultHandphone = new Handphone(DEFAULT_HANDPHONE);
            HomePhone defaultHomePhone = new HomePhone(DEFAULT_HOME_PHONE);
            OfficePhone defaultOfficePhone = new OfficePhone(DEFAULT_OFFICE_PHONE);
            Email defaultEmail = new Email(DEFAULT_EMAIL);
            Address defaultAddress = new Address(DEFAULT_ADDRESS);
            PostalCode defaultPostalCode = new PostalCode(DEFAULT_POSTAL_CODE);
            Debt defaultDebt = new Debt(DEFAULT_DEBT);
            Interest defaultInterest = new Interest(DEFAULT_INTEREST);
            Deadline defaultDeadline = new Deadline(DEFAULT_DEADLINE);
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
            this.person = new Person(defaultName, defaultHandphone, defaultHomePhone, defaultOfficePhone, defaultEmail,
                    defaultAddress, defaultPostalCode, defaultDebt, defaultInterest, defaultDeadline, defaultTags);
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
     * Sets the {@code PostalCode} and {@code Cluster} of the {@code Person} that we are building.
     */
    public PersonBuilder withPostalCode(String postalCode) {
        try {
            this.person.setPostalCode(new PostalCode(postalCode));
            this.person.setCluster(new Cluster(this.person.getPostalCode()));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("postal code is expected to be unique.");
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
     * Sets the {@code Handphone} of the {@code Person} that we are building.
     */
    public PersonBuilder withHandphone(String handphone) {
        try {
            this.person.setHandphone(new Handphone(handphone));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("handphone is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code HomePhone} of the {@code Person} that we are building.
     */
    public PersonBuilder withHomePhone(String homePhone) {
        try {
            this.person.setHomePhone(new HomePhone(homePhone));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("home phone is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code OfficePhone} of the {@code Person} that we are building.
     */
    public PersonBuilder withOfficePhone(String officePhone) {
        try {
            this.person.setOfficePhone(new OfficePhone(officePhone));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("office phone is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Debt} of the {@code Person} that we are building.
     */
    public PersonBuilder withDebt(String debt) {
        try {
            this.person.setDebt(new Debt(debt));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("debt is expected to be unique.");
        }
        return this;
    }

    //@@author jelneo
    /**
     * Sets the {@code totalDebt} of the {@code Person} that we are building.
     */
    public PersonBuilder withTotalDebt(String debt) {
        try {
            this.person.setTotalDebt(new Debt(debt));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("debt is expected to be unique.");
        }
        return this;
    }
    //@@author

    /**
     * Sets the {@code Interest} of the {@code Person} that we are building.
     */
    public PersonBuilder withInterest(String interest) {
        try {
            this.person.setInterest(new Interest(interest));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("interest is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code Person} that we are building.
     */
    public PersonBuilder withDeadline(String deadline) {
        try {
            this.person.setDeadline(new Deadline(deadline));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("deadline is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code DateBorrow} of the {@code Person} that we are building.
     */
    public PersonBuilder withDateBorrow(String dateBorrow) {
        this.person.setDateBorrow(new DateBorrow(dateBorrow));
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

    public Person build() {
        return this.person;
    }

}
