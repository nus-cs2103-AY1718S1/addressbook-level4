package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.HomeNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.SchEmail;
import seedu.address.model.person.Website;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new HomeNumber("65822291"),
                        new Email("alexyeoh@example.com"), new SchEmail("e0011223@u.nus.edu"),
                        new Website("https://www.facebook.com/alexYeoh"),
                        new Address("Blk 30 Geylang Street 29, #06-40"), new Birthday("12/11/1998"), false,
                        getTagSet("friends")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new HomeNumber("65731231"),
                        new Email("berniceyu@example.com"), new SchEmail("e3445566@u.ntu.edu"),
                    new Website("https://www.facebook.com/berniceYu"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Birthday("17/04/1950"), false,
                        getTagSet("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new HomeNumber("61112223"),
                        new Email("charlotte@example.com"), new SchEmail("charlotte@u.ntu.edu"),
                    new Website("https://www.facebook.com/charlotteO"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Birthday("05/03/2001"), false,
                        getTagSet("neighbours")),
                new Person(new Name("David Li"), new Phone("91031282"), new HomeNumber("61231234"),
                        new Email("lidavid@example.com"), new SchEmail("david_li@u.nus.edu"),
                    new Website("https://www.facebook.com/davidLi"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Birthday("06/07/1982"), false,
                        getTagSet("family")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new HomeNumber("65512340"),
                        new Email("irfan@example.com"), new SchEmail("irfan_ibrahim@u.ntu.edu"),
                    new Website("https://www.facebook.com/irfanIbra"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Birthday("10/12/2005"), false,
                        getTagSet("classmates")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new HomeNumber("68910234"),
                        new Email("royb@example.com"), new SchEmail("roy_99@vjc.com.sg"),
                    new Website("https://www.facebook.com/royBala"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Birthday("22/09/1975"), false,
                        getTagSet("colleagues"))
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
