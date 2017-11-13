package seedu.address.model.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthdate;
import seedu.address.model.person.EmailAddress;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Photo;
import seedu.address.model.person.UserId;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new EmailAddress("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new Photo("data/images/default.jpeg"),
                    getTagSet("friends"), new Birthdate("01/01/1995"), new UserId("-")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new EmailAddress("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Photo("data/images/default.jpeg"),
                    getTagSet("colleagues", "friends"), new Birthdate("01/04/1995"), new UserId("-")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new EmailAddress("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new Photo("data/images/default.jpeg"), getTagSet("neighbours"), new Birthdate("01/03/1995"),
                        new UserId("-")),
                new Person(new Name("David Li"), new Phone("91031282"), new EmailAddress("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Photo("data/images/default.jpeg"),
                    getTagSet("family"), new Birthdate("01/01/1995"), new UserId("-")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new EmailAddress("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Photo("data/images/default.jpeg"),
                    getTagSet("classmates"), new Birthdate("01/01/1990"), new UserId("-")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new EmailAddress("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Photo("data/images/default.jpeg"),
                    getTagSet("colleagues"), new Birthdate("01/02/1995"), new UserId("-")),
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        } catch (IOException e) {
            throw new AssertionError("default image should exist");
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) throws IllegalValueException {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
