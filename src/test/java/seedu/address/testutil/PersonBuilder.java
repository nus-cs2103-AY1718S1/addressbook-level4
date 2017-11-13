package seedu.address.testutil;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.MatricNo;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.timetable.Timetable;
import seedu.address.model.photo.PhotoPath;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_GENDER = "Female";
    public static final String DEFAULT_MATRIC_NO = "A0134118K";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_BIRTHDAY = "23051997";
    public static final String DEFAULT_TIMETABLE_URL = "http://modsn.us/HXDrJ";
    public static final String DEFAULT_REMARK = "";
    public static final String DEFAULT_PHOTOPATH = "src/main/resources/images/defaultPhoto.jpg";
    public static final String DEFAULT_TAGS = "friends";

    private Person person;

    public PersonBuilder() {
        try {
            Name defaultName = new Name(DEFAULT_NAME);
            Gender defaultGender = new Gender(DEFAULT_GENDER);
            MatricNo defaultMatricNo = new MatricNo(DEFAULT_MATRIC_NO);
            Phone defaultPhone = new Phone(DEFAULT_PHONE);
            Email defaultEmail = new Email(DEFAULT_EMAIL);
            Address defaultAddress = new Address(DEFAULT_ADDRESS);
            Timetable defaultTimetable = new Timetable(DEFAULT_TIMETABLE_URL);
            Remark defaultRemark = new Remark(DEFAULT_REMARK);
            PhotoPath defaultPhotoPath = new PhotoPath("");
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
            Birthday defaultBirthday = new Birthday(DEFAULT_BIRTHDAY);
            this.person = new Person(defaultName, defaultGender, defaultMatricNo, defaultPhone, defaultEmail,
                    defaultAddress, defaultTimetable, defaultRemark, defaultPhotoPath,
                    defaultTags, defaultBirthday);
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
     * Sets the {@code Gender} of the {@code Person} that we are building.
     */
    public PersonBuilder withGender(String gender) {
        try {
            this.person.setGender(new Gender(gender));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("gender is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Person} that we are building.
     */
    public PersonBuilder withMatricNo(String matricNo) {
        try {
            this.person.setMatricNo(new MatricNo(matricNo));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("matric number is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.person.setRemark(new Remark(remark));
        return this;
    }

    /**
     * Sets the {@code Photo Path} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhotoPath(String photoPath) {
        try {
            this.person.setPhotoPath(new PhotoPath(photoPath));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("photo path is expected to be unique.");
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

    //@@author CindyTsai1
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

    //@@author zacharytang
    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withTimetable(String url) {
        try {
            this.person.setTimetable(new Timetable(url));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("timetable is expected to be unique.");
        }
        return this;
    }

    //@@author
    public Person build() {
        return this.person;
    }
}
