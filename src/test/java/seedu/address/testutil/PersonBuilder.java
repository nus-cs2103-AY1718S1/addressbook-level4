package seedu.address.testutil;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.FormClass;
import seedu.address.model.person.Grades;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PostalCode;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "student: 97272031 parent: 97979797";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_FORMCLASS = "6E1";
    public static final String DEFAULT_GRADES = "123.0";
    public static final String DEFAULT_POSTALCODE = "123456";
    public static final String DEFAULT_REMARK = "";
    public static final String DEFAULT_TAGS = "friends";

    private Person person;

    public PersonBuilder() {
        try {
            Name defaultName = new Name(DEFAULT_NAME);
            Phone defaultPhone = new Phone(DEFAULT_PHONE);
            Email defaultEmail = new Email(DEFAULT_EMAIL);
            Address defaultAddress = new Address(DEFAULT_ADDRESS);
            FormClass defaultFormClass = new FormClass(DEFAULT_FORMCLASS);
            Grades defaultGrades = new Grades(DEFAULT_GRADES);
            PostalCode defaultPostalCode = new PostalCode(DEFAULT_POSTALCODE);
            Remark defaultRemark = new Remark(DEFAULT_REMARK);
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
            this.person = new Person(defaultName, defaultPhone, defaultEmail, defaultAddress, defaultFormClass,
                    defaultGrades, defaultPostalCode, defaultRemark, defaultTags);
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

    //@@author lincredibleJC
    /**
     * Sets the {@code FormClass} of the {@code Person} that we are building.
     */
    public PersonBuilder withFormClass(String formClass) {
        try {
            this.person.setFormClass(new FormClass(formClass));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("formClass is expected to be unique.");
        }
        return this;
    }
    //@@author

    //@@author lincredibleJC
    /**
     * Sets the {@code Grades} of the {@code Person} that we are building.
     */
    public PersonBuilder withGrades(String grades) {
        try {
            this.person.setGrades(new Grades(grades));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("grades are expected to be unique.");
        }
        return this;
    }
    //@@author

    /**
     * Sets the {@code PostalCode} of the {@code Person} that we are building.
     */
    public PersonBuilder withPostalCode(String postalCode) {
        try {
            this.person.setPostalCode(new PostalCode(postalCode));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("postal code is expected to be unique.");
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

    //@@author nahtanojmil
    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {

        this.person.setRemark(new Remark(remark));
        return this;
    }
    //@@author

    public Person build() {
        return this.person;
    }

}
