package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.FormClass;
import seedu.address.model.person.Grades;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PostalCode;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[]{
                new Person(new Name("Alex Yeoh"), new Phone("student/97272031 parent/97979797 "),
                            new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                            new FormClass("6E1"), new Grades("123.0"), new PostalCode("123456"),
                            new Remark("I am a man"), getTagSet("friends")),
                new Person(new Name("Bernice Yu"), new Phone("student/97272031 parent/97979797 "),
                            new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Garden, #07-18"),
                            new FormClass("6E1"), new Grades("123.0"), new PostalCode("654321"),
                            new Remark("Woman"), getTagSet("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("student/97272031 parent/97979797 "),
                            new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                            new FormClass("6E1"), new Grades("123.0"), new PostalCode("987654"),
                            new Remark("Awesome mate"), getTagSet("neighbours")),
                new Person(new Name("David Li"), new Phone("student/97272031 parent/97979797 "),
                            new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Garden Street 26, #16-43"),
                            new FormClass("12S23"), new Grades("123.0"), new PostalCode("676767"),
                            new Remark("Git Gud"), getTagSet("family")),
                new Person(new Name("Irfan Ibrahim"), new Phone("student/97272031 parent/97979797 "),
                            new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                            new FormClass("12s23"), new Grades("123.0"), new PostalCode("999999"),
                            new Remark("Look at me"), getTagSet("classmates")),
                new Person(new Name("Roy Balakrishnan"), new Phone("student/97272031 parent/97979797 "),
                            new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                            new FormClass("12s23"), new Grades("123.0"), new PostalCode("999666"),
                            new Remark("Yay done"), getTagSet("colleagues"))
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
