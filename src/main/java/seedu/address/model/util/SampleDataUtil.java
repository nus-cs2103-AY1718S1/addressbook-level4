package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.ExpiryDate;
import seedu.address.model.person.Group;
import seedu.address.model.person.Image;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), getTagSet("friends"),
                    new ExpiryDate(""), new Remark(""), new Group("none"), new Image("")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends"), new ExpiryDate(""), new Remark(""),
                    new Group("none"), new Image("")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("neighbours"), new ExpiryDate(""), new Remark(""),
                    new Group("none"), new Image("")),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("family"), new ExpiryDate(""), new Remark(""),
                    new Group("none"), new Image("")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("classmates"), new ExpiryDate(""), new Remark(""),
                    new Group("none"), new Image("")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("colleagues"), new ExpiryDate(""), new Remark(""),
                    new Group("none"), new Image("")),
                new Person(new Name("Don Porter"), new Phone("85833142"), new Email("donport@example.com"),
                    new Address("25 Lower Kent Ridge Rd"),
                    getTagSet("colleagues"), new ExpiryDate(""), new Remark(""),
                    new Group("none"), new Image("")),
                new Person(new Name("Ashley Lao"), new Phone("9872123"), new Email("ashll@example.com"),
                    new Address("South Tower"),
                    getTagSet("colleagues"), new ExpiryDate(""), new Remark(""),
                    new Group("none"), new Image("")),
                new Person(new Name("Kevin Tan"), new Phone("53522389"), new Email("kevt@example.com"),
                    new Address("Prince George Park"),
                    getTagSet("classmates"), new ExpiryDate(""), new Remark(""),
                    new Group("none"), new Image("")),
                new Person(new Name("James Lim"), new Phone("4453236"), new Email("jamesl@example.com"),
                    new Address("Marina Bay Sands"),
                    getTagSet("classmates"), new ExpiryDate(""), new Remark(""),
                    new Group("none"), new Image("")),
                new Person(new Name("Sulimen"), new Phone("7573463"), new Email("slm@example.com"),
                    new Address("Woodlands"),
                    getTagSet("classmates"), new ExpiryDate(""), new Remark(""),
                    new Group("none"), new Image("")),
                new Person(new Name("Grant Miller"), new Phone("7573463"), new Email("grantm@example.com"),
                    new Address("Ridge View Residences"),
                    getTagSet("classmates"), new ExpiryDate(""), new Remark(""),
                    new Group("none"), new Image("")),
                new Person(new Name("Ahmed Ali"), new Phone("7573463"), new Email("slm@example.com"),
                    new Address("UTown"),
                    getTagSet("classmates"), new ExpiryDate(""), new Remark(""),
                    new Group("none"), new Image("")),
                new Person(new Name("Joey Lau"), new Phone("7573463"), new Email("slm@example.com"),
                    new Address("UTown"),
                    getTagSet("classmates"), new ExpiryDate(""), new Remark(""),
                    new Group("none"), new Image(""))
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
