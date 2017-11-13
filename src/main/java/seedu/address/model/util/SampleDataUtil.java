package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.FacebookUsername;
import seedu.address.model.person.FileImage;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.reminder.DueDate;
import seedu.address.model.reminder.Priority;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderDetails;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            //@@author yangminxingnus
            return new Person[] {
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new DateOfBirth("13.10.1997"),
                    new Remark("CS2105/LEC/1,CS2104/LEC/1"), new FileImage(""),
                    new FacebookUsername(""), getTagSet("friends")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new DateOfBirth("13.10.1997"),
                    new Remark("CS2101/SEC/1,CS2102/LEC/1"), new FileImage(""),
                    new FacebookUsername(""), getTagSet("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new DateOfBirth("13.10.1997"),
                    new Remark("CS2104/LEC/1,CS2102/LEC/1"), new FileImage(""),
                    new FacebookUsername(""),    getTagSet("neighbours")),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new DateOfBirth("13.10.1997"),
                    new Remark("CS2101/SEC/1,CS2102/LEC/1"), new FileImage(""),
                    new FacebookUsername(""), getTagSet("family")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new DateOfBirth("13.10.1997"),
                    new Remark("CS2101/SEC/1,CS2102/LEC/1"), new FileImage(""),
                    new FacebookUsername(""), getTagSet("classmates")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new DateOfBirth("13.10.1997"),
                    new Remark("CS2101/SEC/1,CS2102/LEC/1"), new FileImage(""),
                    new FacebookUsername(""),
                    getTagSet("colleagues")),
                new Person(new Name("Ronak Lakhotia"), new Phone("93911558"), new Email("email@gmail.com"),
                    new Address("Prince Georges Park"), new DateOfBirth(("13.10.1997")),
                    new Remark("CS2101/SEC/1,CS2102/LEC/1"), new FileImage(""),
                    new FacebookUsername(""),
                    getTagSet("colleagues"))
            };
            //@@author
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }
    public static Reminder[] getSampleReminder() {

        try {
            return new Reminder[]{
                new Reminder(new ReminderDetails("CS2103T Assignment"), new Priority(
                            "Priority Level: High"), new DueDate("12.11.2017")),
                new Reminder(new ReminderDetails("Group meeting"), new Priority(
                        "Priority Level: High"), new DueDate("12.11.2017"))
            };
        } catch (IllegalValueException ive) {
            throw new AssertionError("Date cannot be invalid");
        }
    }


    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            for (Reminder sampleReminder : getSampleReminder()) {
                sampleAb.addReminder(sampleReminder);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        } catch (DuplicateReminderException ef) {
            throw new AssertionError("sample data cannot contain duplicate reminders", ef);
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
