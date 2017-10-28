package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.mod.Mod;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Photo;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.task.Appointment;
import seedu.address.model.task.Date;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Task;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alex Yeoh"), getPhoneSet("87438807"), new Birthday("17/05/1995"),
                    getEmailSet("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    new Photo("images/defaultPhoto.png"), getModSet("CS2101")),
                new Person(new Name("Bernice Yu"), getPhoneSet("99272758"), new Birthday("08/01/1991"),
                    getEmailSet("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        new Photo("images/defaultPhoto.png"), getModSet("CS2103", "CS2101")),
                new Person(new Name("Charlotte Oliveiro"), getPhoneSet("93210283"),
                    new Birthday("20/11/1987"), getEmailSet("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new Photo("images/defaultPhoto.png"), getModSet("CS2103")),
                new Person(new Name("David Li"), getPhoneSet("91031282"), new Birthday("29/02/1997"),
                    getEmailSet("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new Photo("images/defaultPhoto.png"), getModSet("GEQ1000")),
                new Person(new Name("Irfan Ibrahim"), getPhoneSet("92492021"),
                    new Birthday("01/01/1976"), getEmailSet("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    new Photo("images/defaultPhoto.png"), getModSet("GER1000")),
                new Person(new Name("Roy Balakrishnan"), getPhoneSet("92624417"),
                    new Birthday("13/09/1966"), getEmailSet("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new Photo("images/defaultPhoto.png"), getModSet("CFG1010"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Appointment("Meeting"), new Date("27/10/2017"), new StartTime("12:00")),
                new Task(new Appointment("Birthday"), new Date("30/11/2017"), new StartTime("12:00"))
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
            for (Task sampleTask : getSampleTasks()) {
                sampleAb.addTask(sampleTask);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    /**
     * Returns a phone set containing the list of strings given.
     */
    public static Set<Phone> getPhoneSet(String... strings) throws IllegalValueException {
        HashSet<Phone> phones = new HashSet<>();
        for (String s : strings) {
            phones.add(new Phone(s));
        }

        return phones;
    }

    /**
     * Returns a email set containing the list of strings given.
     */
    public static Set<Email> getEmailSet(String... strings) throws IllegalValueException {
        HashSet<Email> emails = new HashSet<>();
        for (String s : strings) {
            emails.add(new Email(s));
        }

        return emails;
    }

    /**
     * Returns a mod set containing the list of strings given.
     */
    public static Set<Mod> getModSet(String... strings) throws IllegalValueException {
        HashSet<Mod> mods = new HashSet<>();
        for (String s : strings) {
            mods.add(new Mod(s));
        }

        return mods;
    }

}
