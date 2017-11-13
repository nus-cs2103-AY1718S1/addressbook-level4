package seedu.address.testutil;

import java.util.HashMap;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthdate;
import seedu.address.model.person.EmailAddress;
import seedu.address.model.person.Name;
import seedu.address.model.person.NusModules;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Photo;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UserId;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@example.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_STORED_PHOTO = "data/images/alice@example.com.jpg";
    public static final String DEFAULT_TAGS = "friends";
    public static final String DEFAULT_BIRTHDATE = "12/12/1995";
    public static final String DEFAULT_USERID = "-";

    private Person person;

    public PersonBuilder() {
        try {
            Name defaultName = new Name(DEFAULT_NAME);
            Phone defaultPhone = new Phone(DEFAULT_PHONE);
            EmailAddress defaultEmail = new EmailAddress(DEFAULT_EMAIL);
            Address defaultAddress = new Address(DEFAULT_ADDRESS);
            Photo defaultPhoto = new Photo(DEFAULT_STORED_PHOTO, 0);
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
            Birthdate defaultBirthdate = new Birthdate(DEFAULT_BIRTHDATE);
            UserId defaultUserId = new UserId(DEFAULT_USERID);
            this.person = new Person(defaultName, defaultPhone, defaultEmail, defaultAddress, defaultPhoto,
                    defaultTags, defaultBirthdate, defaultUserId);
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
    public PersonBuilder withEmailAddress(String email) {
        try {
            this.person.setEmailAddress(new EmailAddress(email));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("email is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Photo} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhoto(String photo) {
        this.person.setPhoto(new Photo(photo, 0));
        return this;
    }

    /**
     * Sets the {@code Birthdate} of the {@code Person} that we are building.
     */
    //@@author hengyu95
    public PersonBuilder withBirthdate(String birthdate) {
        try {
            this.person.setBirthdate(new Birthdate(birthdate));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("birthdate is expected.");
        }
        return this;
    }

    /**
     * Sets the {@code UserID} of the {@code Person} that we are building.
     */

    public PersonBuilder withUserId(String id) {
        try {
            this.person.setUserId(new UserId(id));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("id is expected.");
        }
        return this;
    }
    //@@author

    //@@author ritchielq
    /**
     * Sets the {@code NusModules} of the {@code Person} that we are building.
     */
    public PersonBuilder withNusModules(String nusModule) {
        NusModules newNusModules = new NusModules();

        String[] lessons = nusModule.split("&");
        for (String lesson : lessons) {
            int index1 = lesson.indexOf("[");
            int index2 = lesson.indexOf("]");
            int index3 = lesson.indexOf("=");
            String moduleCode = lesson.substring(0, index1);
            String lessonType = lesson.substring(index1 + 1, index2);
            String lessonSlot = lesson.substring(index3 + 1);

            HashMap<String, String> lessonHm = new HashMap<>();
            lessonHm.put(lessonType, lessonSlot);
            try {
                newNusModules = newNusModules.addModule(moduleCode, lessonHm);
            } catch (IllegalValueException e) {
                throw new IllegalArgumentException("nusModule is wrong format.");
            }
        }
        this.person.setNusModules(newNusModules);

        return this;
    }

    //@@author
    public Person build() {
        return this.person;
    }

}
