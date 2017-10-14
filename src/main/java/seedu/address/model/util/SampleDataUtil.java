package seedu.address.model.util;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Bloodtype;
import seedu.address.model.person.Email;
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
            return new Person[]{
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"), new Bloodtype("A"),
                        getTagSet("friends"), new Remark(""),
                        new Appointment("Alex Yeoh")),

                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Bloodtype("B"),
                        getTagSet("colleagues", "friends"), new Remark("Owes me Money"),
                        new Appointment("Bernice Yu")),

                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                        new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                        new Bloodtype("AB"),
                        getTagSet("neighbours"), new Remark("Has her birthday next week"),
                        new Appointment("Charlotte Oliveiro")),

                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Bloodtype("O"),
                        getTagSet("family"), new Remark("My Project Partner"),
                        new Appointment("David Li")),

                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"), new Bloodtype("A"),
                        getTagSet("classmates"), new Remark("I have an appointment with him next Saturday"),
                        new Appointment("Irfan Ibrahim")),

                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"), new Bloodtype("O"),
                        getTagSet("colleagues"), new Remark("Typing a long string to make sure it can get "
                        + "displayed even though the string is long. StringStringString"
                        + "StringStringStringStringStringStringStringStringStringString"
                        + "StringStringStringStringStringString"),
                        new Appointment("Roy Balakrishnam"))
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
            tags.add(new Tag(s, Tag.DEFAULT_COLOR));
        }

        return tags;
    }

    /**
     * Returns a calendar that is set to 2018/08/20 23:12
     */
    private static Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, 8, 20, 23, 12);
        return calendar;
    }
}
