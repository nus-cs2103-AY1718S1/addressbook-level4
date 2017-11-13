package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.FacebookAddress;
import seedu.address.model.person.GoogleId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Desc;
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
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"), //head
                new Address("Blk 30 Geylang Street 29, #06-40"), new Birthday("03041987"),
                new FacebookAddress("Alex"), getTagSet("friends"), new GoogleId("not GoogleContact")),

                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Birthday("28031990"),
                    new FacebookAddress("BerniceYu"), getTagSet("colleagues", "friends"),
                    new GoogleId("not GoogleContact")),

                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Birthday("18111993"),
                    new FacebookAddress("CharlotteO"), getTagSet("neighbours"),
                    new GoogleId("not GoogleContact")),

                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Birthday("15072010"),
                    new FacebookAddress("davidli"), getTagSet("family"), new GoogleId("not GoogleContact")),

                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Birthday("17121994"),
                    new FacebookAddress("ii"), getTagSet("classmates"), new GoogleId("not GoogleContact")),

                new Person(new Name("Kelvin Naomi"), new Phone("96847231"), new Email("kelvin@example.com"),
                    new Address("Blk 13 Naomi Street 20, #17-35"), new Birthday("12121994"),
                    new FacebookAddress("kelvin"), getTagSet("classmates"), new GoogleId("not GoogleContact")),

                new Person(new Name("Lovell Josephine"), new Phone("96847232"), new Email("Lovell@example.com"),
                    new Address("Blk 23 Josephine Street 20, #17-35"), new Birthday("12101967"),
                    new FacebookAddress("Lovell"), getTagSet("schoolmates"), new GoogleId("not GoogleContact")),

                new Person(new Name("Shevon Ocean"), new Phone("96847233"), new Email("Ocean@example.com"),
                    new Address("Blk 33 Shevon Street 20, #17-35"), new Birthday("12111994"),
                    new FacebookAddress("Shevon"), getTagSet("friends"), new GoogleId("not GoogleContact")),

                new Person(new Name("Reagan Deforest"), new Phone("96847243"), new Email("Reagen@example.com"),
                    new Address("Blk 322 Reagen Street 20, #17-35"), new Birthday("12111974"),
                    new FacebookAddress("kelvin"), getTagSet("friends"), new GoogleId("not GoogleContact")),

                new Person(new Name("Roxy Glenda"), new Phone("96842233"), new Email("Glenda@example.com"),
                    new Address("Blk 323 Roxy Street 20, #17-35"), new Birthday("12111994"),
                    new FacebookAddress("Glenda"), getTagSet("friends", "colleagues"),
                    new GoogleId("not GoogleContact")),

                new Person(new Name("Jamey Lillia"), new Phone("95684343"), new Email("Lillia@example.com"),
                    new Address("Blk 312 Lillia Street 22, #12-34"), new Birthday("23101994"),
                    new FacebookAddress("Lillia"), getTagSet("friends"), new GoogleId("not GoogleContact")),

                new Person(new Name("Kaylee Bryanna"), new Phone("96842233"), new Email("Bryanna@example.com"),
                    new Address("Blk 323 Bryanna Street 20, #17-35"), new Birthday("12031994"),
                    new FacebookAddress("Kaylee"), getTagSet("friends", "colleagues"),
                    new GoogleId("not GoogleContact")),

                new Person(new Name("Gervase Steve"), new Phone("95748321"), new Email("Gervase@example.com"),
                    new Address("Blk 32 Steve Street 22, #2-34"), new Birthday("12102994"),
                    new FacebookAddress("GervaseSteve"), getTagSet("friends"),
                    new GoogleId("not GoogleContact")),

                new Person(new Name("Brendon Bram"), new Phone("96584743"), new Email("Brendon@example.com"),
                    new Address("Blk 323 Brendon Street 9, #9-35"), new Birthday("12031994"),
                    new FacebookAddress("Brendon"), getTagSet("friends", "classmates", "neighbours"),
                    new GoogleId("not GoogleContact")),

                new Person(new Name("Dane Edric"), new Phone("96823133"), new Email("Edric@example.com"),
                    new Address("Blk 968 Edric Street 54, #13-22"), new Birthday("12111992"),
                    new FacebookAddress("Edric"), getTagSet("friends", "colleagues"),
                    new GoogleId("not GoogleContact")),

                new Person(new Name("Tawnee Merilyn"), new Phone("95684343"), new Email("Tawnee@example.com"),
                    new Address("Blk 685 Tawnee Street 22, #14-34"), new Birthday("09091993"),
                    new FacebookAddress("Tawnee"), getTagSet("friends"),
                    new GoogleId("not GoogleContact")),

                new Person(new Name("Ellen Daryl"), new Phone("96842233"), new Email("Ellen@example.com"),
                    new Address("Blk 657 Ellen Street 20, #6-52"), new Birthday("12071994"),
                    new FacebookAddress("Ellen"), getTagSet("friends", "colleagues"),
                    new GoogleId("not GoogleContact")),

                new Person(new Name("Sonnie Kate"), new Phone("95742321"), new Email("Sonnie@example.com"),
                    new Address("Blk 32 Sonnie Street 22, #2-34"), new Birthday("12111973"),
                    new FacebookAddress("GervaseSteve"), getTagSet("friends"),
                    new GoogleId("not GoogleContact")),

                new Person(new Name("Alanis Otis"), new Phone("96584743"), new Email("Otis@example.com"),
                    new Address("Blk 323 Alanis Street 9, #9-35"), new Birthday("10101956"),
                    new FacebookAddress("Otis"), getTagSet("friends", "classmates", "neighbours"),
                    new GoogleId("not GoogleContact")),

                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Birthday("03081970"),
                    new FacebookAddress("roy"), getTagSet("colleagues"), new GoogleId("not GoogleContact"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    //@@author srishag
    public static Task[] getSampleTasks() {
        try {
            return new Task[]{
                new Task(new Header("Submission"), new Desc("Project portfolio"), new Deadline("today")),
                new Task(new Header("Shopping"), new Desc("Buy groceries"), new Deadline("tomorrow")),
                new Task(new Header("Chores"), new Desc("Washing clothes"), new Deadline("tomorrow, 5 pm")),
                new Task(new Header("Project"), new Desc("Finish Verilog code"), new Deadline("13/12/2017")),
                new Task(new Header("Vacation plans"), new Desc("Plan for holiday"), new Deadline("tomorrow")),
                new Task(new Header("Homework"), new Desc("Page 6 to 9"), new Deadline("21/11/2017")),
                new Task(new Header("Submission"), new Desc("Project report"), new Deadline("today")),
                new Task(new Header("Assignment"), new Desc("Tutorial homework"), new Deadline("17/12/2017"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }
    //@@author

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
        } catch (DuplicatePersonException | DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate persons or tasks", e);
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
