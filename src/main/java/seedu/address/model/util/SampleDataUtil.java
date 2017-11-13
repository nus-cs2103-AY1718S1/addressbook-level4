package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.DisplayPicture;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nickname;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PopularityCounter;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.reminders.Date;
import seedu.address.model.reminders.ReadOnlyUniqueReminderList;
import seedu.address.model.reminders.Reminder;
import seedu.address.model.reminders.Time;
import seedu.address.model.reminders.UniqueReminderList;
import seedu.address.model.reminders.exceptions.DuplicateReminderException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new Birthday("21/10/1995"), new Nickname("Albert"),
                    new DisplayPicture(""), new PopularityCounter(0), getTagSet("friends")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Birthday("22/10/1995"),
                    new Nickname(""), new DisplayPicture(""), new PopularityCounter(0),
                        getTagSet("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Birthday("23/10/1995"),
                    new Nickname("Charl"), new DisplayPicture(""), new PopularityCounter(0),
                        getTagSet("neighbours")),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Birthday("24/10/1995"),
                    new Nickname(""), new DisplayPicture(""), new PopularityCounter(0),
                        getTagSet("family")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Birthday("25/10/1995"), new Nickname(""),
                    new DisplayPicture(""), new PopularityCounter(0), getTagSet("classmates")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Birthday("26/10/1995"), new Nickname(""),
                    new DisplayPicture(""), new PopularityCounter(0), getTagSet("colleagues")),
                new Person(new Name("Alice Tan"), new Phone("83292191"), new Email("alicet@example.com"),
                     new Address("35, Clementi, #11-31"), new Birthday("16/11/1999"), new Nickname("ali"),
                     new DisplayPicture(""), new PopularityCounter(0), getTagSet("cs1010")),
                new Person(new Name("Rahul"), new Phone("93242829"), new Email("rahul@example.com"),
                     new Address("Lokmanaya Nagar, 440016, Nagpur"), new Birthday("15/11/1999"), new Nickname(""),
                     new DisplayPicture(""), new PopularityCounter(0), getTagSet("cs1010", "utown")),
                new Person(new Name("Bob"), new Phone("83292191"), new Email("bob@example.com"),
                     new Address("35, Yio Chu Kang, #11-31"), new Birthday("16/1/1999"), new Nickname(""),
                     new DisplayPicture(""), new PopularityCounter(0), getTagSet("cs1010")),
                new Person(new Name("Janice"), new Phone("92938829"), new Email("janicecool@example.com"),
                     new Address("21, Dover, 112932"), new Birthday("16/5/1999"), new Nickname(""),
                     new DisplayPicture(""), new PopularityCounter(0), getTagSet("cs1010")),
                new Person(new Name("Akshay"), new Phone("62782292"), new Email("akshay@example.com"),
                     new Address("Changi, 1232922"), new Birthday("4/11/2000"), new Nickname(""),
                     new DisplayPicture(""), new PopularityCounter(0),
                        getTagSet("friends", "colleagues")),
                new Person(new Name("Rachael"), new Phone("72927739"), new Email("rachel@example.com"),
                     new Address("Changi, 124289"), new Birthday("21/11/2000"), new Nickname(""),
                     new DisplayPicture(""), new PopularityCounter(0),
                        getTagSet("colleagues")),
                new Person(new Name("Alex"), new Phone("83292191"), new Email("alex@example.com"),
                     new Address("35, Jurong East"), new Birthday("10/2/1950"), new Nickname(""),
                     new DisplayPicture(""), new PopularityCounter(0), getTagSet("family")),
                new Person(new Name("Zee Leon"), new Phone("9292923"), new Email("zeeleon@example.com"),
                     new Address("35, Mountain View, Google"), new Birthday("17/11/1950"), new Nickname(""),
                     new DisplayPicture(""), new PopularityCounter(0), getTagSet("nusStudentClub")),
                new Person(new Name("Yow rin"), new Phone("82929278"), new Email("yow@example.com"),
                     new Address("Twin towers malaysia"), new Birthday("10/5/2001"), new Nickname(""),
                     new DisplayPicture(""), new PopularityCounter(0), getTagSet("nusStudentClub")),
                new Person(new Name("Wayne Lee"), new Phone("9287478"), new Email("lee@example.com"),
                     new Address("Twin towers malaysia"), new Birthday("10/2/2001"), new Nickname(""),
                     new DisplayPicture(""), new PopularityCounter(0), getTagSet("nusStudentClub")),
                new Person(new Name("Maria"), new Phone("29339282"), new Email("maria@example.com"),
                     new Address("Jurong West, 1182022"), new Birthday("10/12/1996"), new Nickname(""),
                     new DisplayPicture(""), new PopularityCounter(0), getTagSet("nusStudentClub")),
                new Person(new Name("Eugene Tan"), new Phone("92283828"), new Email("eugie@example.com"),
                     new Address("Lentor Avenue, Singapore"), new Birthday("18/11/1996"), new Nickname(""),
                     new DisplayPicture(""), new PopularityCounter(0), getTagSet("enemy")),
                new Person(new Name("Nathan"), new Phone("92283828"), new Email("nath@example.com"),
                     new Address("Bintan, Indonesia"), new Birthday("18/7/1996"), new Nickname(""),
                     new DisplayPicture(""), new PopularityCounter(0), getTagSet("intern")),
                new Person(new Name("Piyush"), new Phone("28728828"), new Email("piyuswag@example.com"),
                     new Address("Toronto, Canada"), new Birthday("4/9/1996"), new Nickname(""),
                     new DisplayPicture(""), new PopularityCounter(0), getTagSet("basketballTeam")),
                new Person(new Name("Maria"), new Phone("9288222"), new Email("mariaprincess@example.com"),
                     new Address("Jurong West, 1182022"), new Birthday("10/12/1996"), new Nickname(""),
                     new DisplayPicture(""), new PopularityCounter(0), getTagSet("friend"))
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

    public static Reminder[] getSampleReminders() {
        try {
            return new Reminder[] {
                new Reminder("Email CS1010 Students about Midterm", new Date("17-11-2017"),
                            new Time("19:00")),
                new Reminder("Birthday Party Eugene", new Date("17-11-2017"),
                            new Time("22:00")),
                new Reminder("CS2103T Release jar", new Date("15-11-2017"),
                            new Time("12:00")),
                new Reminder("CS2105 Assignment Due", new Date("22-11-2017"),
                            new Time("23:59")),
                new Reminder("Survey for Product", new Date("19-11-2017"),
                            new Time("23:59")),
                new Reminder("Fix Bugs of iContacts", new Date("04-12-2017"),
                            new Time("10:00"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }
    //@@author tshradheya

    public static ReadOnlyUniqueReminderList getSampleReminderList() {
        try {
            UniqueReminderList sampleReminderList = new UniqueReminderList();
            for (Reminder sampleReminder : getSampleReminders()) {
                sampleReminderList.add(sampleReminder);
            }
            return sampleReminderList;
        } catch (DuplicateReminderException e) {
            throw new AssertionError("sample data cannot contain duplicate reminders", e);
        }
    }
    //@@author

}
