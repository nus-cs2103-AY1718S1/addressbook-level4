package seedu.address.testutil;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.FacebookUsername;
import seedu.address.model.person.FileImage;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_IMAGE = "";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_DATE_OF_BIRTH = "13.10.1997";
    public static final String DEFAULT_REMARK = "CS2101/SEC/1";
    public static final String DEFAULT_TAGS = "friends";
    public static final String DEFAULT_USERNAME = "ronak.lakhotia";

    private Person person;

    public PersonBuilder() {
        try {
            Name defaultName = new Name(DEFAULT_NAME);
            Phone defaultPhone = new Phone(DEFAULT_PHONE);
            Email defaultEmail = new Email(DEFAULT_EMAIL);
            Address defaultAddress = new Address(DEFAULT_ADDRESS);
            DateOfBirth defaultDateOfBirth = new DateOfBirth(DEFAULT_DATE_OF_BIRTH);
            Remark defaultRemark = new Remark(DEFAULT_REMARK);
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
            FileImage defaultImage = new FileImage(DEFAULT_IMAGE);
            FacebookUsername defaultusername = new FacebookUsername(DEFAULT_USERNAME);

            this.person = new Person(defaultName, defaultPhone, defaultEmail,
                    defaultAddress, defaultDateOfBirth, defaultRemark, defaultImage, defaultusername, defaultTags);
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
     * Sets the {@code DateOfBirth} of the {@code Person} that we are building.
     */
    public PersonBuilder withDateOfBirth(String Date) {
        try {
            this.person.setDateOfBirth(new DateOfBirth(Date));

        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Unique birthday expected");
        }
        return this;
    }
    /**
     * Sets the {@code FileImage} of the {@code Person} that we are building.
     */
    public PersonBuilder withImage(String image) {
        try {
            this.person.setImage(new FileImage(image));

        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Unique image expected");
        }
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        try {
            this.person.setRemark(new Remark(remark));
        } catch (IllegalValueException e) {
            System.out.println();
        }
        return this;
    }
    /**
     * Sets the {@code FacebookUsername} of the {@code Person} that we are building.
     */
    public PersonBuilder withUsername(String username) {
        try {
            this.person.setUsername(new FacebookUsername(username));
        } catch (IllegalValueException e) {
            System.out.println();
        }
        return this;
    }

    public Person build() {
        return this.person;
    }

}
