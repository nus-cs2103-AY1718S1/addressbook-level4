package seedu.address.testutil;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Photo;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.phone.Phone;
import seedu.address.model.person.phone.UniquePhoneList;
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
    public static final String DEFAULT_TAGS = "friends";
    public static final String DEFAULT_ADDITIONAL_PHONE1 = "24444";
    public static final String DEFAULT_ADDITIONAL_PHONE2 = "25555";

    private Person person;

    public PersonBuilder() {
        try {
            Name defaultName = new Name(DEFAULT_NAME);
            Phone defaultPhone = new Phone(DEFAULT_PHONE);
            Email defaultEmail = new Email(DEFAULT_EMAIL);
            Address defaultAddress = new Address(DEFAULT_ADDRESS);
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
            UniquePhoneList defaultPhoneList = new UniquePhoneList(new Phone(DEFAULT_ADDITIONAL_PHONE1));
            defaultPhoneList.add(new Phone(DEFAULT_ADDITIONAL_PHONE2));
            this.person = new Person(defaultName, defaultPhone, defaultEmail, defaultAddress,
                    defaultTags, defaultPhoneList);
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
            throw new IllegalArgumentException("tag name violation.");
        }
        return this;
    }

    //@@author willxujun
    /**
     * Set the {@code tags} to the person we are building
     */
    public PersonBuilder withTags(Set<Tag> tags) {
        CollectionUtil.elementsAreUnique(tags);
        this.person.setTags(tags);
        return this;
    }
    //@@author

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
     * UniquePhoneList
     */
    public PersonBuilder withPhoneList(String... phones) {
        try {
            UniquePhoneList list = new UniquePhoneList();
            for (String phone : phones) {
                list.add(new Phone(phone));
            }
            this.person.setPhoneList(list);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("This is not an additional phone number.");
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
     * Parses the {@code customFields} into a {@code Set<CustomField>}
     * and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withCustomFields(String ... customFields) {
        try {
            this.person.setCustomFields(SampleDataUtil.getCustomFieldSet(customFields));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Photo} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhoto(String photo) {
        try {
            this.person.setPhoto(new Photo(photo));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("photo is expected to be unique.");
        }
        return this;
    }

    public Person build() {
        return this.person;
    }

}
