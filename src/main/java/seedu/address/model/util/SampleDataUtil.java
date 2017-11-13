package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.DateTimeParserUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Header;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[]{
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new Birthday("01/01/1901"), new Remark(""),
                    getTagSet("friends"), false, true, false),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Birthday("11/11/1995"),
                    new Remark("Likes to swim."), getTagSet("colleagues", "friends"),
                    false, false, false),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new Birthday("02/02/1902"), new Remark(""), getTagSet("neighbours"),
                    false, false, false),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Birthday("09/08/1993"),
                    new Remark("As quick as a leopard."), getTagSet("family"),
                    false, false, false),
                new Person(new Name("Fenton Elliot"), new Phone("89910293"), new Email("fentone@example.com"),
                    new Address("Blk 45 Woodlands Street 3, #04-23"), new Birthday("15/09/1990"), new Remark(""),
                    getTagSet("colleagues"), false, false, false),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Birthday("22/10/1985"), new Remark(""),
                    getTagSet("classmates"), false, false, false),
                new Person(new Name("Jamie Oliveoil"), new Phone("80992010"), new Email("jamieo@example.com"),
                    new Address("Blk 83 Holly Street 85, #02-13"), new Birthday("01/10/2010"), new Remark(""),
                    getTagSet("niece"), false, false, false),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Birthday("15/12/1991"), new Remark(""),
                    getTagSet("colleagues"), false, true, false),
                new Person(new Name("Wilson Loh"), new Phone("89102937"), new Email("wilsonl@example.com"),
                    new Address("Blk 39 Yishun Street 12, #10-11"), new Birthday("03/02/1995"), new Remark(""),
                    getTagSet("friends"), false, false, false),
                new Person(new Name("Zachary Quek"), new Phone("9123417"), new Email("zacq@example.com"),
                    new Address("Blk 45 Clementi Ave 6, #01-31"), new Birthday("01/12/1980"), new Remark(""),
                    getTagSet("colleagues"), false, false, false)
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample person data cannot be invalid", e);
        }
    }

    //@@author Alim95
    public static Task[] getSampleTask() {
        try {
            return new Task[]{
                new Task(new Header("Pick up a new book")),
                new Task(new Header("Learn basic Thai")),
                new Task(new Header("Update collation of codes"),
                        DateTimeParserUtil.nattyParseDateTime("yesterday")),
                new Task(new Header("Go grocery shopping"),
                        DateTimeParserUtil.nattyParseDateTime("today")),
                new Task(new Header("Help Jimmy with math"),
                        DateTimeParserUtil.nattyParseDateTime("tonight")),
                new Task(new Header("Go for fishing trip"),
                        DateTimeParserUtil.nattyParseDateTime("tmr night")),
                new Task(new Header("Fetch Jimmy from soccer practise"),
                        DateTimeParserUtil.nattyParseDateTime("two days later")),
                new Task(new Header("Go to the gym"),
                        DateTimeParserUtil.nattyParseDateTime("2 days later")),
                new Task(new Header("Clean up house with Jimmy"),
                        DateTimeParserUtil.nattyParseDateTime("this weekend")),
                new Task(new Header("Go for parent teacher meeting"),
                        DateTimeParserUtil.nattyParseDateTime("next week")),
                new Task(new Header("Pay off debt"),
                        DateTimeParserUtil.nattyParseDateTime("next week")),
                new Task(new Header("Look for new recipes"),
                        DateTimeParserUtil.nattyParseDateTime("next week")),
                new Task(new Header("Finish up project"),
                        DateTimeParserUtil.nattyParseDateTime("two weeks later")),
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample task data cannot be invalid", e);
        }
    }
    //@@author

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            for (Task sampleTask : getSampleTask()) {
                sampleAb.addTask(sampleTask);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate task", e);
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
