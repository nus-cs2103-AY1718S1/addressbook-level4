package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.module.Module;
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
                new Person(new Name("Alice Pauline"), getPhoneSet("85355255"), new Birthday("25/09/1990"),
                    getEmailSet("alice@example.com"), new Address("123, Jurong West Ave 6, #08-111"),
                    new Photo("images/defaultPhoto.png"), getModuleSet("CS1231", "CS1010")),
                new Person(new Name("Benson Meier"), getPhoneSet("98765432"), new Birthday("12/02/1985"),
                    getEmailSet("johnd@example.com"),
                    new Address("311, Clementi Ave 2, #02-25"),
                        new Photo("images/defaultPhoto.png"), getModuleSet("CS1020", "CS1010")),
                new Person(new Name("Carl Kurz"), getPhoneSet("95352563"),
                    new Birthday("03/12/1973"), getEmailSet("heinz@example.com"),
                    new Address("wall street"),
                    new Photo("images/defaultPhoto.png"), getModuleSet("CS1020", "CS1231")),
                new Person(new Name("Daniel Meier"), getPhoneSet("87652533"), new Birthday("21/09/1988"),
                    getEmailSet("cornelia@example.com"),
                    new Address("10th street"),
                    new Photo("images/defaultPhoto.png"), getModuleSet("CS1010")),
                new Person(new Name("Elle Meyer"), getPhoneSet("9482224"),
                    new Birthday("04/08/1991"), getEmailSet("werner@example.com"),
                    new Address("michigan ave"),
                    new Photo("images/defaultPhoto.png"), getModuleSet("CS1010")),
                new Person(new Name("Fiona Kunz"), getPhoneSet("9482427"),
                    new Birthday("01/10/1989"), getEmailSet("lydia@example.com"),
                    new Address("little tokyo"),
                    new Photo("images/defaultPhoto.png"), getModuleSet("CS1010")),
                new Person(new Name("George Best"), getPhoneSet("9482442"),
                    new Birthday("15/05/1993"), getEmailSet("anna@example.com"),
                    new Address("4th street"),
                    new Photo("images/defaultPhoto.png"), getModuleSet("CS1010"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }
    // @@author tanchc
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Appointment("Meeting"), new Date("30/10/2017"), new StartTime("18:00")),
                new Task(new Appointment("Soccer"), new Date("15/12/2017"), new StartTime("09:00")),
                new Task(new Appointment("Birthday"), new Date("30/11/2017"), new StartTime("19:30")),
                new Task(new Appointment("Work"), new Date("01/01/2018"), new StartTime("08:00")),
                new Task(new Appointment("Homework"), new Date("12/01/2018"), new StartTime("23:59")),
                new Task(new Appointment("Exam"), new Date("05/12/2017"), new StartTime("17:00")),
                new Task(new Appointment("Competition"), new Date("25/11/2017"), new StartTime("12:00"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }
    // @@author
    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            // @@author tanchc
            for (Task sampleTask : getSampleTasks()) {
                sampleAb.addTask(sampleTask);
            }
            // @@author
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    // @@author ahmadalkaff
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
    // @@author

    /**
     * Returns a module set containing the list of strings given.
     */
    public static Set<Module> getModuleSet(String... strings) throws IllegalValueException {
        HashSet<Module> modules = new HashSet<>();
        for (String s : strings) {
            modules.add(new Module(s));
        }

        return modules;
    }

}
