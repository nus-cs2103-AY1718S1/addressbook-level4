package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Facebook;
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
        Person[] personList = new Person[6];
        try {
            personList[0] = new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new Birthday("1991/01/22"), new Facebook("4"),
                    getTagSet("friends"));
            personList[1] = new Person(new Name("Bernice Yu"), new Phone("99272758"),
                    new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Birthday("1990/10/20"),
                    new Facebook("6815841748"), getTagSet("colleagues", "friends"));
            personList[2] = new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new Birthday("1890/03/20"), new Facebook("153080620724"), getTagSet("neighbours"));
            personList[3] = new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Birthday("1999/01/01"),
                    new Facebook("771663635"), getTagSet("family"));
            personList[4] = new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Birthday("2000/01/31"), new Facebook("5"),
                    getTagSet("classmates"));
            personList[5] = new Person(new Name("Roy Balakrishnan"), new Phone("92624417"),
                    new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new Birthday("2001/07/07"), new Facebook("6"), getTagSet("colleagues"));
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }

        // set sample remark for data
        personList[0].setRemark(new Remark("Likes pineapple"));
        personList[1].setRemark(new Remark("Likes dragon fruit"));
        personList[2].setRemark(new Remark("Likes music"));
        personList[3].setRemark(new Remark("Likes apple juice"));
        personList[4].setRemark(new Remark("Likes coffee"));
        personList[5].setRemark(new Remark("Likes milk tea"));

        return personList;
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
